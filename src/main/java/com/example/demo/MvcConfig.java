package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String ROOT_DIRECTORY;
    @Value("${static.path}")
    private String STATIC_FOLDER_PATH;

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/" + ROOT_DIRECTORY);

        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:/" + STATIC_FOLDER_PATH);

    }
}


