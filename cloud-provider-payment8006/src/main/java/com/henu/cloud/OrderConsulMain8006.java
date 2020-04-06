package com.henu.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderConsulMain8006 {
    public static void main(String[] args) {
        SpringApplication.run(OrderConsulMain8006.class,args);
    }
}
