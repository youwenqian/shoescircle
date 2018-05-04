package com.shoes.scarecrow.persistence.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-4-29
 * Time 上午11:15
 */
@Data
public class UserCondition extends BaseCondition implements Serializable {
    private String userName;
    private String id;
    private String nickName;
    private String taobaoName;
    private String stockId;
    private String confirmPassword;
}
