package com.shoes.scarecrow.persistence.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-5-3
 * Time 下午11:25
 */
@Data
public class StockCondition extends BaseCondition implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer batchNo;
    private Integer goodsId;
    private Integer goodsExtendId;
    private Integer shareFlag;
    private String storeAddress;
    private String goodsName;
}
