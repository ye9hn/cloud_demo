package com.henu.cloud;

import com.henu.robin.RibbonRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//eureka client
@EnableEurekaClient
//开启ribbon，使用RibbonRule配置的负载均衡策略
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = RibbonRule.class)
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class,args);
    }
}
