package com.shoes.scarecrow.persistence.service;

import com.shoes.scarecrow.persistence.domain.GoodExtend;
import com.shoes.scarecrow.persistence.mappers.GoodsExtendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-5-4
 * Time 上午10:31
 */
@Service
public class GoodsExtendService {
    @Autowired
    private GoodsExtendMapper goodsExtendMapper;
    public List<GoodExtend> getGoodExtend(GoodExtend goodExtend) {
        return goodsExtendMapper.queryByCondition(goodExtend);
    }

    public int saveGoodExtend(GoodExtend goodExtend) {
        return goodsExtendMapper.insert(goodExtend);
    }
}
