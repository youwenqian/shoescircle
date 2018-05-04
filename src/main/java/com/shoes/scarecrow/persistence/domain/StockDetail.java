package com.shoes.scarecrow.persistence.domain;

import lombok.Data;

import java.util.Date;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-5-3
 * Time 下午11:37
 */
@Data
public class StockDetail {
    private Integer id;

    private Integer userId;

    private String batchNo;

    private Integer goodsId;

    private String goodsName;

    private Integer goodsExtendId;

    private Integer sizeId;

    private String sizeName;

    private Integer colorId;

    private String colorName;

    private Double intoPrice;

    private Integer qty;

    private Date storageTime;

    private Integer shareFlag;

    private String storeAddress;

    private String remark;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer status;

    private Integer yn;
}
