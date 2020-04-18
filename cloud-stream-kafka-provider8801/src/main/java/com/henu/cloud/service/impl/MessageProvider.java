package com.henu.cloud.service.impl;

import com.henu.cloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

@EnableBinding(Source.class)//定义消息的推送管道
@Slf4j
public class MessageProvider implements IMessageProvider {
   @Autowired
   private MessageChannel output;
    @Override
    public String send() {
        String serial= UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        log.info("**** serial:{}",serial);
        return serial;
    }
}
