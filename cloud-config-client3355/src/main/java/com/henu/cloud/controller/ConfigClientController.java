package com.henu.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope//这个客户端自动刷新要加上
public class ConfigClientController {
    @Value("${server.port}")
    String configInfo;
    @GetMapping("/configInfo")
    public String configInfo(){
        return configInfo;
    }
}
