package com.shoes.scarecrow.persistence.mappers;

import com.shoes.scarecrow.persistence.domain.GoodExtend;
import com.shoes.scarecrow.persistence.domain.Size;
import com.shoes.scarecrow.persistence.domain.SizeCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("goodsExtendMapper")
public interface GoodsExtendMapper {
    int insert(GoodExtend goodExtend);

    List<GoodExtend> queryByCondition(GoodExtend  goodExtend);

    int update(GoodExtend goodExtend);

    int deleteByUserId(@Param("userId") int userId);
}