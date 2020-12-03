package com.glen.glengen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
//java.lang.ClassCastException: org.springframework.orm.jpa.EntityManagerHolder cannot be cast to org.springframework.orm.hibernate5.SessionHolder
@ComponentScan("com.glen")
@EnableCaching
@EntityScan( basePackages = {"com.glen.glengen.entity"})
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class }
)
// 开启事务管理
@EnableTransactionManagement(proxyTargetClass = true)//启用注解事务，即可以使用@Transactional注解来控制事务等同于xml配置方式的 <tx:annotation-driven />
@EnableAspectJAutoProxy
public class GlenGenApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlenGenApplication.class, args);
    }

}
