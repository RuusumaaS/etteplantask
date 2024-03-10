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
                .apiInfo(apiInfoMaintenanceTask());
    }
    
    @Bean
    public Docket factoryDeviceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("factorydevice")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.etteplan.servicemanual.factorydevice"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfoFactoryDevice());
    }

    private ApiInfo apiInfoFactoryDevice() {
        return new ApiInfoBuilder()
                .title("Etteplan maintenancetask work")
                .description("API documentation for your Spring Boot application")
                .version("1.0")
                .build();
    }
    
    private ApiInfo apiInfoMaintenanceTask() {
        return new ApiInfoBuilder()
                .title("Etteplan maintenancetask work")
                .description("Documentation for maintenance tasks. Always use JSON as request bodies. You never give factorydevice"
                        + "in request body. If that is needed, it is given in path. Regoistrationtimes are also handled in code. Severity and status"
                        + "are optional for you to write as they have default values specified in code. Every query returns the maintenancetasks.")
                .version("1.0")
                .build();
    }
}

