package com.elearning.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/video/**")
                .addResourceLocations("file:uploads/videos/");

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}