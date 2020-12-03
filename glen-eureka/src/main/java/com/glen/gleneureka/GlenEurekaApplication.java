package com.glen.gleneureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class GlenEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlenEurekaApplication.class, args);
    }

}
