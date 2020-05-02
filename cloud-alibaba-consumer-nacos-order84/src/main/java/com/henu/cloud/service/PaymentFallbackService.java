package com.henu.cloud.service;

import com.henu.cloud.entities.CommonResult;
import com.henu.cloud.entities.Payment;
import org.springframework.stereotype.Component;

/**
 *  PaymentService实现类,调用服务失败兜底方法
 *
 */
@Component
public class PaymentFallbackService implements PaymentService{
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(4444,"服务降级返回，PaymentFallbackService",new Payment(1L,"errorService"));
    }
}
