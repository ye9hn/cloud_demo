package com.henu.cloud.service;

import com.henu.cloud.entities.CommonResult;
import com.henu.cloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 测试openfeign服务调用
 */
@FeignClient(value = "nacos-payment-provider",fallback = PaymentFallbackService.class)
public interface PaymentService {
    @GetMapping("/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id")Long id);
}
