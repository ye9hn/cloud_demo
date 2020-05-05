package com.henu.cloud.dao;

import com.henu.cloud.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {
    //1、创建订单
    void create(Order order);
    //2、修改订单状态，从零改为1
    void update(@Param("userId")Long userId,@Param("status")Integer status);
}
