package com.shoes.scarecrow.persistence.mappers;

import com.shoes.scarecrow.persistence.domain.Order;
import com.shoes.scarecrow.persistence.domain.OrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-5-3
 * Time 下午9:45
 */
@Repository
public interface OrderMapper {
    int insertOrder(Order order);

    List<OrderDetail> getOrders(@Param("userId") Integer userId);

    int updateOrderById(@Param("userId") Integer userId,@Param("id") Integer id);
}
