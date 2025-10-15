package com.train.business;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.train.business", "com.train.common"})
@MapperScan("com.train.business.mapper")
public class BusinessApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }
}
