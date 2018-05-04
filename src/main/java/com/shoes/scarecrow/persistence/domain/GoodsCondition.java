package com.shoes.scarecrow.persistence.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangyucheng
 * @description
 * @create 2018/3/14 20:58
 */
@Data
public class GoodsCondition extends BaseCondition implements Serializable {

    private static final long serialVersionUID = -1203687943734944473L;

    private Integer userId;//用户id

    private String keyword;//关键字

    private Integer goodsClass;//分类id

    private String goodsName;

    private Integer brandId;//品牌id

    private Integer sex;//男女

    private Integer status;

    private Integer sizeId;//尺码id

    private Integer colorId;//颜色id

}