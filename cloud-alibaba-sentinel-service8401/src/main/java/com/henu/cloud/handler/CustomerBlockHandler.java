package com.henu.cloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.henu.cloud.entities.CommonResult;
import com.henu.cloud.entities.Payment;

public class CustomerBlockHandler {
    public static CommonResult handlerException(BlockException exception){
        return new CommonResult(4444,"按客户自定义，handlerException----1",new Payment(2020L,"serial003"));
    }

    public static CommonResult handlerException2(BlockException exception){
        return new CommonResult(4444,"按客户自定义，handlerException----2",new Payment(2020L,"serial003"));
    }
}
