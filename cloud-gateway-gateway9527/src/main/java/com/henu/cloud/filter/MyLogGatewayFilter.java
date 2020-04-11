package com.henu.cloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * 定义gateway的filter 做过滤操作 ： pre 业务 post
 */
@Component
@Order(0)
@Slf4j
public class MyLogGatewayFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

       log.info("****come in MyLogGatewayFilter:{}",new Date());
       String uname=exchange.getRequest().getQueryParams().getFirst("uname");
       if (uname==null){
           log.info("******用户名为null,非法用户");
           exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
           return exchange.getResponse().setComplete();
       }
        return chain.filter(exchange);
    }
}
