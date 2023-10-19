package com.cyu.cyuojbackendgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//扫描该包将其注册为bean,需要使用的时候将其注入
@EnableDiscoveryClient
public class CyuojBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CyuojBackendGatewayApplication.class, args);
    }

}
