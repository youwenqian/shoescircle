package com.shoes.scarecrow.persistence.domain;

import lombok.Data;

import java.util.Date;

@Data
public class GoodsDetail {
    private Integer id;

    private Integer userId;

    private Double price;

    private String keyword;

    private Integer goodsClass;

    private String goodsName;

    private String goodsDesc;

    private Integer qty;

    private String remark;

    private Integer brandId;

    private Integer sex;

    private Integer status;

    private Integer goodsExtendId;

    private Integer sizeId;

    private Integer colorId;

}