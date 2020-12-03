package com.glen.terminnal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.glen")
@MapperScan("com.glen.terminnal.dao")
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class TerminnalApplication {

    public static void main(String[] args) {
        SpringApplication.run(TerminnalApplication.class, args);
    }

}
