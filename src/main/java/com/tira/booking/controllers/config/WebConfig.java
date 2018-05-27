package com.tira.booking.controllers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/images/**")
                .addResourceLocations("file:./resources/static/images/");
       /*registry.addResourceHandler("/index.html")
                .addResourceLocations("file:./resources/static/public/index.html");
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("file:./resources/static/public/assets/");
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("file:./resources/static/public/fonts/");*/
    }
}