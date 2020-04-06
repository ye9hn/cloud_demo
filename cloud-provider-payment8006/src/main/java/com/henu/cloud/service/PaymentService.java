package com.henu.cloud.service;

import com.henu.cloud.entities.Payment;

public interface PaymentService{
    public int create(Payment payment);
    public Payment getPaymentById(Long id);
}
