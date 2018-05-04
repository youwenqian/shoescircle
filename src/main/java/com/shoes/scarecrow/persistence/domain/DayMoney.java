package com.shoes.scarecrow.persistence.domain;

import lombok.Data;

/**
 * 每天出入库的金额
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-5-4
 * Time 下午5:13
 */
@Data
public class DayMoney {
    private String createTime;//出入库时间
    private Integer total;//金额
}
