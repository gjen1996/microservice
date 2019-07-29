package com.glen.appcustomerlogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.glen")
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class AppCustomerLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppCustomerLoginApplication.class, args);
    }

}
