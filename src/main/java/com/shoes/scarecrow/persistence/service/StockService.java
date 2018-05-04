package com.shoes.scarecrow.persistence.service;

import com.shoes.scarecrow.persistence.domain.DayMoney;
import com.shoes.scarecrow.persistence.domain.Stock;
import com.shoes.scarecrow.persistence.domain.StockCondition;
import com.shoes.scarecrow.persistence.domain.StockDetail;
import com.shoes.scarecrow.persistence.mappers.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangyucheng
 * @description
 * @create 2018/3/10 18:50
 */
@Service
public class StockService {

    @Autowired
    protected StockMapper stockMapper;

    public int saveStock(Stock stock){
        return stockMapper.insert(stock);
    }


    public int getStockDetailCountByCondition(StockCondition condition) {
        return stockMapper.getDetailCountByCondition(condition);
    }

    public List<StockDetail> getStockDetailByCondition(StockCondition condition) {
        return stockMapper.getDetailByCondition(condition);
    }

    public int deleteByUserIdAndId(Stock stock) {
        return stockMapper.deleteByUserIdAndId(stock);
    }

    public int updateStock(Stock stock) {
        return stockMapper.updateStock(stock);
    }

    public List<DayMoney> getSevenInStockFlow(Integer userId, Integer days) {
        return stockMapper.getSevenInStock(userId,days);
    }

    public Integer getStockTodayPrice(String userId) {
        return stockMapper.getStockTodayPrice(userId);
    }

}