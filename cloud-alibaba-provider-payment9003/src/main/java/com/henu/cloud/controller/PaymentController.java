package com.henu.cloud.controller;

import com.henu.cloud.entities.CommonResult;
import com.henu.cloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;
    public static HashMap<Long, Payment> hashMap=new HashMap<>();
    static {
        hashMap.put(1L,new Payment(1L, UUID.randomUUID().toString().replaceAll("-","")));
        hashMap.put(2L,new Payment(2L, UUID.randomUUID().toString().replaceAll("-","")));
        hashMap.put(3L,new Payment(3L, UUID.randomUUID().toString().replaceAll("-","")));
    }
    @GetMapping("/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id")Long id){
        Payment payment=hashMap.get(id);
        CommonResult<Payment> result=new CommonResult<>(200,"from mysql serverPort: "+serverPort,payment);
        return result;
    }
}
