/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springboot/CfgProperties.java to edit this template
 */
package com.etteplan.servicemanual.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

     @Bean
    public Docket maintenanceTaskApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("maintenancetask")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.etteplan.servicemanual.maintenancetask"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
    
    @Bean
    public Docket factoryDeviceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("factorydevice")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.etteplan.servicemanual.factorydevice"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Etteplan maintenancetask work")
                .description("API documentation for your Spring Boot application")
                .version("1.0")
                .build();
    }
}

