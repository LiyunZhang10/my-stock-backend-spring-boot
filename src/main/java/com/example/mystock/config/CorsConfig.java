package com.example.mystock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // 配置 CORS 规则
                registry.addMapping("/**") // 允许所有路径进行跨域请求
                        .allowedOrigins("http://localhost:8083", "http://zhangliyun10.gnway.cc")  // 允许来自这些域名的请求
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的 HTTP 请求方法
                        .allowedHeaders("*")  // 允许的请求头
                        .allowCredentials(false);  // 不允许携带凭证
            }
        };
    }
}