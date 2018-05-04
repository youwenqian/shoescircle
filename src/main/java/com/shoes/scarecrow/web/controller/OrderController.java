package com.shoes.scarecrow.web.controller;

import com.shoes.scarecrow.persistence.domain.Order;
import com.shoes.scarecrow.persistence.domain.OrderDetail;
import com.shoes.scarecrow.persistence.service.OrderService;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-4-28
 * Time 上午12:43
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    private static Logger log = Logger.getLogger(OrderController.class);

    @Autowired
    public OrderService orderService;
    @RequestMapping("/addOrder")
    @ResponseBody
    public Map addOrder(Order order, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info(session.getAttribute("userName")+"进入到增加订单方法,订单详情:"+mapper.writeValueAsString(order));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        order.setBuyerId(userId);
        order.setCreateTime(new Date());
        order.setStatus(0);
        order.setYn(1);
        int n = orderService.insertOrder(order);
        try {
            log.info(session.getAttribute("userName")+"离开到增加订单方法,订单详情:"+mapper.writeValueAsString(order));
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("success",true);
        return map;
    }
    @RequestMapping("/getOrders")
    @ResponseBody
    public Map getOrder(HttpSession session){
        Map<String,Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        log.info(session.getAttribute("userName")+"进入到查询订单方法,用户id="+session.getAttribute("userId"));
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        List<OrderDetail> list = orderService.getOrders(userId);
        try {
            log.info(session.getAttribute("userName")+"离开到查询订单方法,用户id="+session.getAttribute("userId")+",result="+mapper.writeValueAsString(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("flag",true);
        if(list==null){
            list = new ArrayList<>();
        }
        map.put("data",list);
        return map;
    }
    @RequestMapping("/updateOrder")
    @ResponseBody
    public Map updateOrder(HttpSession session ,Integer id){
        Map<String,Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        log.info(session.getAttribute("userName")+"进入到处理订单方法,订单id="+id);
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        int n = orderService.updateOrderById(userId,id);
        if(n>0){
            map.put("flag",true);
        }else {
            map.put("flag",false);
        }
        log.info(session.getAttribute("userName")+"离开到处理订单方法,订单id="+id);
        return map;
    }
}
