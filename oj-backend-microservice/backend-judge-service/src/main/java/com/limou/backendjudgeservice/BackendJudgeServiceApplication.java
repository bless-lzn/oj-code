package com.limou.backendjudgeservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ComponentScan("com.limou")
public class BackendJudgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendJudgeServiceApplication.class, args);
    }

}
