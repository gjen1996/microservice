package com.glen.glengen.compiler;/**
 * @author Glen
 * @create 2020- 10-2020/10/13-9:56
 * @Description
 */

import org.apache.commons.lang3.builder.ToStringExclude;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyClassReLoader extends ClassLoader {
    private String classPath;
    private String classname;
//    String classname = "com.glen.glengen.entity.TestSuccess";

    public MyClassReLoader(String classpath,String classname) {
        this.classPath = classpath;
        this.classname = classname;
    }

    @Override
    public Class<?> findClass(String packageName) throws ClassNotFoundException {
        byte[] classData = getData(packageName);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(classname, classData, 0, classData.length);
        }
    }

    private byte[] getData(String className) {
        String path = classPath + className;
        try {
            InputStream is = new FileInputStream(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int num = 0;
            while ((num = is.read(buffer)) != -1) {
                stream.write(buffer, 0, num);
            }
            return stream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        try {
//            String path = "D:/project/glen-cloud/glen-gen/target/classes/com/glen/glengen/entity/";
//            MyClassReLoader reloader = new MyClassReLoader(path);
//            Class r = reloader.findClass("TestSuccess.class");
//            System.out.println(r.newInstance());
////            ClassReloader reloader2 = new ClassReloader(path);
//            Class r2 = reloader.findClass("TestSuccess.class");
//            System.out.println(r2.newInstance());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
}

