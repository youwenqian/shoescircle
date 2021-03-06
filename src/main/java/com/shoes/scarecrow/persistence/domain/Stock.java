package com.shoes.scarecrow.persistence.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Stock {
    private Integer id;

    private Integer userId;

    private String batchNo;

    private Integer goodsId;

    private Integer goodsExtendId;

    private Double intoPrice;

    private Integer qty;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
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