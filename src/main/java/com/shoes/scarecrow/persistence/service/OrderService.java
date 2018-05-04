package com.shoes.scarecrow.persistence.service;

import com.shoes.scarecrow.persistence.domain.Order;
import com.shoes.scarecrow.persistence.domain.OrderDetail;
import com.shoes.scarecrow.persistence.mappers.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-5-3
 * Time 下午9:44
 */
@Service("orderService")
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public int insertOrder(Order order) {
        return orderMapper.insertOrder(order);
    }

    public List<OrderDetail> getOrders(Integer userId) {
        return orderMapper.getOrders(userId);
    }

    public int updateOrderById(Integer userId, Integer id) {
        return orderMapper.updateOrderById(userId,id);
    }
}
