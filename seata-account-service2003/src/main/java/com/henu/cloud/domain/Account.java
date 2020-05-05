package com.henu.cloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Long id;

    private Long userId;
    //总额度
    private BigDecimal total;
    //已用
    private BigDecimal used;
    //剩余额度
    private BigDecimal residue;
}
