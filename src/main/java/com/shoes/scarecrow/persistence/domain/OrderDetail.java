package com.shoes.scarecrow.persistence.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-5-5
 * Time 上午3:48
 */
@Data
public class OrderDetail implements Serializable {
    private Integer id;
    private String userName;
    private String goodsName;
    private String typeName;
    private String brandName;
    private String colorName;
    private String sizeName;
    private Date createTime;
    private String remark;
}
