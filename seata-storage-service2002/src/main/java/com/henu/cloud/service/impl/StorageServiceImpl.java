package com.henu.cloud.service.impl;

import com.henu.cloud.dao.StorageDao;
import com.henu.cloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        log.info("------>storage-service扣减库存");
        storageDao.decrease(productId, count);
        log.info("------>storage-service扣减库存结束");
    }
}
