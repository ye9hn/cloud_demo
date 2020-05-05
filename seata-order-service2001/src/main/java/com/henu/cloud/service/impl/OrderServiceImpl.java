package com.henu.cloud.service.impl;

import com.henu.cloud.dao.OrderDao;
import com.henu.cloud.domain.Order;
import com.henu.cloud.service.AccountService;
import com.henu.cloud.service.OrderService;
import com.henu.cloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private StorageService storageService;
    @Autowired
    private AccountService accountService;

    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    @Override
    public void create(Order order) {
        log.info("----->开始创建订单");
        orderDao.create(order);
        log.info("----->订单微服务开始调用库存,做扣减");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("----->订单微服务开始调用库存,做扣减end");

        log.info("----->订单微服务开始调用账户，做扣减");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("----->订单微服务开始调用账户，做扣减end");

        //修改订单状态
        log.info("----->修改订单状态开始");
        orderDao.update(order.getUserId(), 0);
        log.info("----->修改订单状态结束");
        log.info("----->下订单结束了------");
    }

}
