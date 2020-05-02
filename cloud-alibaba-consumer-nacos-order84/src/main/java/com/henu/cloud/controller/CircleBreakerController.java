package com.henu.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.henu.cloud.entities.CommonResult;
import com.henu.cloud.entities.Payment;
import com.henu.cloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class CircleBreakerController {
    public static final String SERVICE_URL = "http://nacos-payment-provider";
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
    // @SentinelResource(value = "fallback")
    // @SentinelResource(value = "fallback" ,fallback = "handlerFallback")//负责java运行时异常的捕获和兜底
    //@SentinelResource(value = "fallback" ,blockHandler = "dealBlock")//负债sentinel控制台配置违规，服务降级
    @SentinelResource(value = "fallback", fallback = "handlerFallback", blockHandler = "dealBlock")//结合使用，同时完成Java运行时异常捕获，和sentinel限流降级等操作
    public CommonResult<Payment> fallback(@PathVariable("id") Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException,空指针异常");
        }
        return result;
    }

    // @SentinelResource(value = "fallback" ,fallback = "handlerFallback")//负责java运行时异常的捕获和兜底
    public CommonResult handlerFallback(@PathVariable("id") Long id, Throwable o) {
        Payment payment = new Payment(id, "null");
        return new CommonResult<>(444, "兜底异常handlerFallback ， exception：" + o.getMessage().toString(), payment);
    }

    //@SentinelResource(value = "fallback" ,blockHandler = "dealBlock")
    public CommonResult dealBlock(@PathVariable("id") Long id, BlockException exception) {
        Payment payment = new Payment(id, "null");
        return new CommonResult<>(445, "blockHandler-sentinel限流，无此流水号blockException" + exception.getMessage(), payment);
    }

    @Autowired
    private PaymentService paymentService;
@GetMapping("/consumer/openfeign/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id")Long id){
        return paymentService.paymentSQL(id);
    }
}
