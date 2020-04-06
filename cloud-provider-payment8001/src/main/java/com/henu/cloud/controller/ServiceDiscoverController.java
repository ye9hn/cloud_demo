package com.henu.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ServiceDiscoverController {
    @Autowired
    private DiscoveryClient discoveryClient;
    @GetMapping("/payment/discover")
    public Object discover(){
        List<String> services = discoveryClient.getServices();
        for (String service:services){
            log.info("**** service:{}",service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance:instances){
            log.info("instance:{}",instance.getServiceId()+" "+instance.getHost()+":"+instance.getPort()+" "+instance.getUri());
        }
        return discoveryClient;
    }
}
