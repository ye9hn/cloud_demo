package com.henu.cloud.controller;

import com.henu.cloud.entities.CommonResult;
import com.henu.cloud.entities.Payment;
import com.henu.cloud.lb.MyLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
/**
 * 调用远程8001微服务
 */
public class OrderController {

    //public static final String PAYMENT_URL="http://localhost:8001";
    //这里不再写死请求地址了，使用微服务名称走eureka注册中心，通过集群实现负债均衡,同时要这样设置RestTemplate
    // @Bean
    //    @LoadBalanced
    //    public RestTemplate restTemplate(){
    //        return new RestTemplate();
    //    }
    public static final String PAYMENT_URL="http://CLOUD-PAYMENT-SERVICE";
    @Autowired
    private RestTemplate template;
    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return template.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id")Long id){
        return template.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    //使用
    @GetMapping("/consumer/payment/getforentity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id")Long id){
        ResponseEntity<CommonResult>entity=template.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
        log.info("header:{}",entity.getHeaders());
        log.info("status code:{}",entity.getStatusCode());
        return entity.getBody();
    }


    @Autowired
    private MyLoadBalancer loadBalancer;
    @Autowired
    private DiscoveryClient discoveryClient;
    private AtomicInteger count=new AtomicInteger(0);
    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances=discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances==null||instances.size()<=0){
            return null;
        }
        ServiceInstance serviceInstance=loadBalancer.instances(instances);
        URI uri=serviceInstance.getUri();
        System.out.println("count:"+count.incrementAndGet());
        return template.getForObject(uri+"/payment/lb",String.class);
    }
}
