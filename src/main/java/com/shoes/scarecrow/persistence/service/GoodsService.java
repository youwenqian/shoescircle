package com.shoes.scarecrow.persistence.service;

import com.shoes.scarecrow.persistence.domain.GoodExtend;
import com.shoes.scarecrow.persistence.domain.Goods;
import com.shoes.scarecrow.persistence.domain.GoodsCondition;
import com.shoes.scarecrow.persistence.domain.GoodsDetail;
import com.shoes.scarecrow.persistence.mappers.GoodsMapper;
import com.shoes.scarecrow.persistence.mappers.GoodsExtendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangyucheng
 * @description
 * @create 2018/3/10 18:49
 */
@Service
public class GoodsService {

    @Autowired
    protected GoodsMapper goodsMapper;

    public int saveGoods(Goods goods){
        int n = goodsMapper.insertGoods(goods);
        return n;
    }

    public List<Goods> queryByCondition(GoodsCondition condition){
        return goodsMapper.queryByCondition(condition);
    }

    public int queryCountByCondition(GoodsCondition condition){
        return goodsMapper.queryCountByCondition(condition);
    }

    public Goods queryById(int id){
        return goodsMapper.queryById(id);
    }

    public int updateGoods(Goods record){
        return goodsMapper.update(record);
    }

    public int deleteByUserIdAndId(Goods goods){
        return goodsMapper.delByUserIdAndId(goods);
    }

    public int queryCountByBuyerCondition(GoodsCondition goodsCondition) {
        return goodsMapper.queryCountByBuyerCondition(goodsCondition);
    }

    public List<GoodsDetail> queryByBuyerCondition(GoodsCondition goodsCondition) {
        return goodsMapper.queryByBuyerCondition(goodsCondition);
    }
}