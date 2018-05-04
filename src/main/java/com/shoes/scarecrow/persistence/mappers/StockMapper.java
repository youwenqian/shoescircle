package com.shoes.scarecrow.persistence.mappers;

import com.shoes.scarecrow.persistence.domain.DayMoney;
import com.shoes.scarecrow.persistence.domain.Stock;
import com.shoes.scarecrow.persistence.domain.StockCondition;
import com.shoes.scarecrow.persistence.domain.StockDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("stockMapper")
public interface StockMapper {
    int insert(Stock record);

    int insertSelective(Stock record);

    int getDetailCountByCondition(StockCondition condition);

    List<StockDetail> getDetailByCondition(StockCondition condition);

    int deleteByUserIdAndId(Stock stock);

    int updateStock(Stock stock);

    List<DayMoney> getSevenInStock(@Param("userId") Integer userId, @Param("days") Integer days);

    Integer getStockTodayPrice(@Param("userId") String userId);
}