package com.FireAlarm.Config;

import com.FireAlarm.HandlerIntercepter.LoginProtectInterceptor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Data
@ConfigurationProperties(prefix = "cors")
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    private String IP;
    private String PORT;

    @Autowired
    private LoginProtectInterceptor loginProtectInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginProtectInterceptor).addPathPatterns("/Station/**");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://"+IP+":"+PORT)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
