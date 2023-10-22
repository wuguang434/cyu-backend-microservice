package com.cyu.cyuojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.cyu.cyuojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.cyu")
//扫描该包将其注册为bean,需要使用的时候将其注入
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.cyu.cyuojbackendserviceclient.service"})
public class CyuojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CyuojBackendUserServiceApplication.class, args);
    }

}
