package com.glen.glensystem.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 盖玉成
 * @date 2020/9/7 11:41
 * @description Swagger配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Spring Boot中使用Swagger2构建RESTFul APIs")
                        .description("example")
                        .termsOfServiceUrl("https://github.com/gjen1996/example")
                        .contact(new Contact("盖玉成", "https://blog.csdn.net/gjen1996", "805596378@qq.com"))
                        .version("1.0")
                        .build())
                // 如果存在多个Docket实例，则每个实例都必须具有此方法提供的唯一组名称。默认值为"default"。
                .groupName("example")
                // 构建 ApiSelectorBuilder 的第一步
                .select()
                // 自定义包扫描
                .apis(RequestHandlerSelectors.any())
                // 过滤到的才有效
                .paths(PathSelectors.any())
                // 构建 ApiSelectorBuilder 的最后一步
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot swagger整合") //文档标题
                .version("1.0") //版本号
                .description("相关接口")//接口概述
                .build();
    }
}
