package com.henu.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class ServiceDiscoverController {
    @Autowired
    private DiscoveryClient discoveryClient;
    @GetMapping("/payment/discover")
    public Object discover(){
        List<String> services = discoveryClient.getServices();
        for (String service:services){
            log.info("service:{}",service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance:instances){
            log.info("instance:{}",instance.getServiceId()+" "+instance.getHost()+":"+instance.getPort()+" "+instance.getUri());
        }
        return discoveryClient;
    }
    @Value("${server.port}")
    private String serverPort;
    //测试负载均衡用
    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try{
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return serverPort;
    }
}
