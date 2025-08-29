package com.limou.backendjudgeservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ComponentScan("com.limou")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.limou.backendserviceclient.service"})
public class BackendJudgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendJudgeServiceApplication.class, args);
    }

}
