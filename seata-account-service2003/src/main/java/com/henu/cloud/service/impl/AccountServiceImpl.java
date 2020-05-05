package com.henu.cloud.service.impl;

import com.henu.cloud.dao.AccountDao;
import com.henu.cloud.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
   @Autowired
    AccountDao accountDao;
    @Override
    public void decrease(Long userId, BigDecimal money) {
        log.info("---->account-service扣减账户余额开始");
        //这里模拟超时异常,使用seata全局事务处理器捕获处理
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        accountDao.decrease(userId, money);
        log.info("---->account-service扣减账户余额结束");

    }
}
