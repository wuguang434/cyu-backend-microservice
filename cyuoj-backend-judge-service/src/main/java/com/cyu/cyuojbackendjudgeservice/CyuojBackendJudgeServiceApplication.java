package com.cyu.cyuojbackendjudgeservice;

import com.cyu.cyuojbackendjudgeservice.rabbitmq.InitRabbitMq;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.cyu")
//扫描该包将其注册为bean,需要使用的时候将其注入
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.cyu.cyuojbackendserviceclient.service"})
public class CyuojBackendJudgeServiceApplication {

    public static void main(String[] args) {
        InitRabbitMq.doInit();
        SpringApplication.run(CyuojBackendJudgeServiceApplication.class, args);
    }
}
