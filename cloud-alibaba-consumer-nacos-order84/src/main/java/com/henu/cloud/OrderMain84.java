package com.henu.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient

//测试openfeign,开启feign客户端
@EnableFeignClients
public class OrderMain84 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain84.class,args);
    }
}
