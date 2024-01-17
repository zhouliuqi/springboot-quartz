package com.zrgj.quartz.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 肘劉祁
 * Swagger配置类
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                //标题
                                .title("文档标题")
                                //描述
                                .description("文档描述")
                                //信息
                                .contact(new Contact().name("作者"))
                                //版本
                                .version("0.1")
                );
    }

    @Bean
    public GroupedOpenApi signApi() {
        return GroupedOpenApi.builder()
                //分组信息
                .group("定时任务")
                //扫描包
                .pathsToMatch("/**")
                .build();
    }





}


