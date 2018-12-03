package com.example.currency.aggregation.config;


import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.MultipartConfigElement;

@Configuration
public class DataConfigaration {
    @Bean(name = "commonsMultipartResolver")
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }


    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        factory.setMaxFileSize("10MB");
        factory.setMaxRequestSize("10MB");

        return factory.createMultipartConfig();
    }
}
