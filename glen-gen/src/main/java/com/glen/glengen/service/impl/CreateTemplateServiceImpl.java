package com.glen.glengen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.glen.glencommonsystem.util.R;
import com.glen.glengen.compiler.MyClassLoader;
import com.glen.glengen.compiler.MyClassReLoader;
import com.glen.glengen.dao.CreateTemplateDao;
import com.glen.glengen.service.CreateTemplateService;
import com.glen.glengen.templates.EntityTemplate;
import com.glen.glengen.util.CompilerUtil;
import com.glen.glengen.util.CopyDirOpeUtil;
import com.glen.glengen.util.FileOperationUtil;
import com.glen.glengen.util.MkdirDirOpeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author ???
 * @date 2020/9/8 16:29
 * @description ?????
 */
@Slf4j
@Service
@Transactional
public class CreateTemplateServiceImpl implements CreateTemplateService {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CreateTemplateDao createTemplateDao;
    String SOURCE_CODE = null;

    @Override
    public R createTables(JSONObject param) throws Exception {
        String packageName = FileOperationUtil.packageNmae(param.getString("packageName"));
        log.info("packageName:" + packageName);
        StringBuffer fileUrl = new StringBuffer(param.getString("startFileUrl"));
        fileUrl.append(packageName);
        log.info("fileUrl:" + fileUrl);
        //格式化文件 带.java 例如UserDao.java
        String classFileName = FileOperationUtil.className(param.getString("className"), false);
        //格式化文件 不带.java 例如UserDao
        String classNameStand = FileOperationUtil.className(param.getString("className"), true);
        //创建文件
        MkdirDirOpeUtil.createFile(fileUrl.toString(), classFileName);
        param.put("fileUrl", fileUrl.toString());
        param.put("classNameStand", classNameStand);
        param.put("classFileName", classFileName);
        SOURCE_CODE = EntityTemplate.EntityTemplateWriteFile(param).getString("SOURCE_CODE");
//        log.info("SOURCE_CODE:" + SOURCE_CODE);
        log.info("z这个是最终数据：" + param);
        //移动文件
        CopyDirOpeUtil.moveFile(fileUrl.toString(), param.getString("endPath"), classFileName);
        //创建文件
        MkdirDirOpeUtil.createFile(fileUrl.toString(), classFileName);
        EntityTemplate.EntityTemplateWriteFile(param);
        //开始动态编
        CompilerUtil.compilerFirstTpye(param);
        ClassLoader pcl = new MyClassLoader(param.getString("classFilePath"));
        Class c = pcl.loadClass(param.getString("packageName") + "." + classNameStand);
        Object t = Class.forName("com.glen.glengen.GlenGenApplication").newInstance();
        log.info("t："+t);
        log.info("对象的类加载器t："+t.getClass().getClassLoader()); //对象的类加载器
//        //String path = "D:/project/glen-cloud/glen-gen/target/classes/com/glen/glengen/entity/";
//        String path = "/Users/gaiyucheng/Desktop/project/glen-cloud/glen-gen/target/classes/com/glen/glengen/entity/";
//        MyClassReLoader reloader = new MyClassReLoader(path,"com.glen.glengen.entity.TestSuccess");
//        Class r = reloader.findClass("TestSuccess.class");
//        //String path1 = "D:/project/glen-cloud/glen-gen/target/classes/com/glen/glengen/config/";
//        String path1 = "/Users/gaiyucheng/Desktop/project/glen-cloud/glen-gen/target/classes/com/glen/glengen/config/";
//        MyClassReLoader reloader1 = new MyClassReLoader(path1,"com.glen.glengen.config.HibernateConfig");
//        Class r1 = reloader1.findClass("HibernateConfig.class");
        Object o = c.newInstance();
        log.info("对象的类加载器："+o.getClass().getClassLoader()); //对象的类加载器
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(c);
        //设置bean属性
        beanDefinitionBuilder.addPropertyValue("id", "1");
        //注册到spring容器中
        beanFactory.registerBeanDefinition(param.getString("classNameStand"), beanDefinitionBuilder.getBeanDefinition());
        log.info("bean:" + applicationContext.getBean(c));
        log.info("c.newInstance():" + o);
        log.info("entityTpye:" + o);
        log.info("c.getClassLoader:" + c.getClassLoader());
        //进行数据存取
        createTemplateDao.createTables(param, c.newInstance());
        return R.ok();
    }
}
