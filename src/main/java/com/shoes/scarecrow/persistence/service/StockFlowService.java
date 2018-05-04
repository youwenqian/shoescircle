package com.shoes.scarecrow.persistence.service;

import com.shoes.scarecrow.persistence.domain.DayBills;
import com.shoes.scarecrow.persistence.domain.DayMoney;
import com.shoes.scarecrow.persistence.domain.StockFlow;
import com.shoes.scarecrow.persistence.mappers.StockFlowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangyucheng
 * @description
 * @create 2018/3/10 18:51
 */
@Service
public class StockFlowService {

    @Autowired
    protected StockFlowMapper stockFlowMapper;

    public int saveStockFlow(StockFlow stockFlow){
        return stockFlowMapper.insert(stockFlow);
    }


    public List<DayMoney> getSevenOutStockFlow(Integer userId, Integer days) {
        return stockFlowMapper.getSevenOutStockFlow(userId,days);
    }

    public List<DayMoney> getSevenInStockFlow(Integer userId, Integer days) {
        return stockFlowMapper.getSevenInStockFlow(userId,days);
    }

    public List<DayBills> getSevenOutbillsGroupDay(Integer userId, Integer days) {
        return stockFlowMapper.getbillsByDay(userId,days);
    }

    public Integer getStockFlowTodayPrice(String userId) {
        return stockFlowMapper.getTodayPrice(userId);
    }
}