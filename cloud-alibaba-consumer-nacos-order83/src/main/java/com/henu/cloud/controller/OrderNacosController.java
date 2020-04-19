package com.henu.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class OrderNacosController {
    @Autowired
    private RestTemplate template;

    @Value("${service-url.nacos-user-service}")
    private String serverURL;
    @GetMapping("/consumer/payment/nacos/{id}")
    public  String paymentInfo(@PathVariable("id")Long id){
        String result=template.getForEntity(serverURL+"/payment/nacos/"+id,String.class).getBody();
        log.info("result:{}",result);
        return result;
    }
}
