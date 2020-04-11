package com.henu.cloud.controller;

import com.henu.cloud.entities.CommonResult;
import com.henu.cloud.entities.Payment;
import com.henu.cloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;
    @Autowired
    private PaymentService paymentService;

    @PostMapping("payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("********插入结果{}" + result);
        if (result > 0) {
            return new CommonResult(200, "插入数据成功,server port" + serverPort, result);
        } else {
            return new CommonResult(444, "插入数据失败,server port" + serverPort, null);
        }
    }

    @GetMapping("payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("********插入结果{}" + payment);
        if (payment != null) {
            return new CommonResult(200, "查询成功,server port" + serverPort, payment);
        } else {
            return new CommonResult(444, "没有对应记录,server port" + serverPort, null);
        }
    }

    //测试负载均衡用
    @GetMapping("/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id")Integer id){
        String result=paymentService.paymentInfo_OK(id);
        log.info("*****result:{}",result);
        return result;
    }
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id")Integer id){
        String result=paymentService.paymentInfo_TimeOut(id);
        log.info("*****result:{}",result);
        return result;
    }
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id")Integer id){
        String result=paymentService.paymentCircuitBreaker(id);
        log.info("*****result:{}",result);
        return result;
    }
}
