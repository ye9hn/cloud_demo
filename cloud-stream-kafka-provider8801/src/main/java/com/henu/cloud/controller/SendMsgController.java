package com.henu.cloud.controller;

import com.henu.cloud.service.IMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMsgController {
    @Autowired
    private IMessageProvider provider;
    @GetMapping("/sendMessage")
    public String sendMsg(){
        return provider.send();
    }
}
