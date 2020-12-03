package com.glen.glengen.config;




import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author 盖玉成
 * @date 2020/9/7 16:38
 * @description HibernateP配置
 */
@Configuration
@Slf4j
@PropertySource(value = { "classpath:bootstrap.yml" })
public class HibernateConfig {
    @Autowired
    private  Environment environment;
    @Value("${spring.jpa.properties.hibernate.current_session_context_class}")
    public  String current_session_context_class;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    public  String hibernate_ddl_auto;
    @Value("${spring.jpa.properties.hibernate.show-sql}")
    public  String hibernate_show_sql;
    @Value("${spring.jpa.properties.hibernate.cache.use_second_level_cache}")
    public  String cache_use_second_level_cache;
    @Value("${spring.jpa.properties.hibernate.cache.use_query_cache}")
    public  String cache_use_query_cache;

    //session factory
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.glen.glengen.entity"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
    // 数据源配置
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springauth?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("glen1996");
        return dataSource;
    }
    //获取hibernate配置
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
        properties.setProperty("hibernate.current_session_context_class", "thread");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show-sql", "false");
        properties.setProperty("hibernate.cache.use_second_level_cache", "false");
        properties.setProperty("hibernate.cache.use_query_cache", "false");
        return properties;
    }
    // 配置jpa事务管理器
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
// 配置实体管理器工厂
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }


//
//    public  Session currentSession() throws HibernateException {
//        Session s = (Session)session.get();
//        //Open a new Session,if this Thread has none yet
//        if(s == null || !s.isOpen()) {
//            s = this.sessionFactory().oopenSession();
//            session.set(s);
//        }
//        return s;
//    }
//
//    public  void closeSession() throws HibernateException {
//        Session s = (Session)session.get();
//        session.set(null);
//        if(s != null) {
//            s.close();
//        }
//    }
}
