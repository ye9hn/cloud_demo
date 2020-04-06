package com.henu.cloud.controller;

import com.henu.cloud.entities.CommonResult;
import com.henu.cloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
/**
 * 调用远程8001微服务
 */
public class OrderZKController {

    //public static final String PAYMENT_URL="http://localhost:8001";
    //这里不再写死请求地址了，使用微服务名称走zookeeper注册中心，通过集群实现负债均衡,同时要这样设置RestTemplate
    // @Bean
    //    @LoadBalanced
    //    public RestTemplate restTemplate(){
    //        return new RestTemplate();
    //    }
    public static final String INVOKE_URL="http://cloud-provider-payment";
    @Autowired
    private RestTemplate template;
    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return template.postForObject(INVOKE_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id")Long id){
        return template.getForObject(INVOKE_URL+"/payment/get/"+id,CommonResult.class);
    }

    @GetMapping("/consumer/payment/zk")
    public String paymentInfo(){
        return template.getForObject(INVOKE_URL+"/payment/zk",String.class);
    }
}
