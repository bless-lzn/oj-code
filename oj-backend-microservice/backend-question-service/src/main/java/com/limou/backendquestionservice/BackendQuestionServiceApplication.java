package com.limou.backendquestionservice;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
@MapperScan("com.limou.backendquestionservice.mapper")
@EnableScheduling
@SpringBootApplication
@ComponentScan("com.limou")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.limou.backendserviceclient.service"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class BackendQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendQuestionServiceApplication.class, args);
    }

}
