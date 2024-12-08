package com.example.mystock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.example.mystock.mapper")
@SpringBootApplication
@EnableScheduling
public class MyStockApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyStockApplication.class, args);
    }

}
