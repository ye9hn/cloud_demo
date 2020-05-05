package com.henu.cloud.controller;

import com.henu.cloud.domain.Order;
import com.henu.cloud.entities.CommonResult;
import com.henu.cloud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/order/create")
    public CommonResult create(Order order){
        System.out.println(order);
        orderService.create(order);
        return new CommonResult(200,"订单创建成功");
    }
}
