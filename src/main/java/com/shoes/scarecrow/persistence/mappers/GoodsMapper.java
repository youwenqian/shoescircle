package com.shoes.scarecrow.persistence.mappers;

import com.shoes.scarecrow.persistence.domain.Goods;
import com.shoes.scarecrow.persistence.domain.GoodsCondition;
import com.shoes.scarecrow.persistence.domain.GoodsDetail;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("goodsMapper")
public interface GoodsMapper {
    int insertGoods(Goods record);

    List<Goods> queryByCondition(GoodsCondition condition);

    int queryCountByCondition(GoodsCondition condition);

    Goods queryById(int id);

    int update(Goods record);

    int delByUserIdAndId(Goods goods);

    int queryCountByBuyerCondition(GoodsCondition goodsCondition);

    List<GoodsDetail> queryByBuyerCondition(GoodsCondition goodsCondition);
}