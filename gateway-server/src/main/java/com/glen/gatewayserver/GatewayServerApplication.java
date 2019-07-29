package com.glen.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.glen")
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayServerApplication{

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

}
