package com.henu.cloud.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA(){
        return "-----testA";
    }
    @GetMapping("/testB")
    public String testB(){
        log.info(Thread.currentThread().getName()+"----testB");
        return "-----testB";
    }
    @GetMapping("/testD")
    public String testD(){
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e){
            log.error(e.toString());
        }
        log.info(Thread.currentThread().getName()+"----testD");
        return "-----testD";
    }

    /**
     * 服务热点规则
     * @param p1
     * @param p2
     * @return
     */
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "dealHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false)String p1,
                             @RequestParam(value = "p2",required = false)String p2){
        log.info(Thread.currentThread().getName()+"----testHotKey");
        return "-----testHotKey";
    }
    public String dealHotKey(String p1, String p2, BlockException exception){
        log.info(Thread.currentThread().getName()+"-----testHotKey is over");
        return "-----testHotKey is over";
    }
}
