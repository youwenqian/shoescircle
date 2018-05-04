package com.shoes.scarecrow.persistence.mappers;

import com.shoes.scarecrow.persistence.domain.DayBills;
import com.shoes.scarecrow.persistence.domain.DayMoney;
import com.shoes.scarecrow.persistence.domain.StockFlow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("stockFlowMapper")
public interface StockFlowMapper {
    int insert(StockFlow record);

    int insertSelective(StockFlow record);

    List<DayMoney> getSevenOutStockFlow(@Param("userId") Integer userId, @Param("days") Integer days);

    List<DayMoney> getSevenInStockFlow(@Param("userId") Integer userId, @Param("days") Integer days);

    List<DayBills> getbillsByDay(@Param("userId") Integer userId, @Param("days") Integer days);

    Integer getTodayPrice(@Param("userId") String userId);
}