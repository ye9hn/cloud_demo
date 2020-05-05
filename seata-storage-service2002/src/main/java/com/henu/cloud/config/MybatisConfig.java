package com.henu.cloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.henu.cloud.dao"})
public class MybatisConfig {
}
