package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wb-lwc235565 on 2018/1/24.
 */
@Configuration
public class ViewConfig extends WebMvcConfigurerAdapter{
   @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("swagger-ui.html");
        registry.addViewController("/").setViewName("swagger-ui.html");
        registry.addViewController("index").setViewName("swagger-ui.html");

    }
}
