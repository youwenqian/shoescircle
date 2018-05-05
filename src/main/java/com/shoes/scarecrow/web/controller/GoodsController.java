package com.shoes.scarecrow.web.controller;

import com.shoes.scarecrow.persistence.domain.Brand;
import com.shoes.scarecrow.persistence.domain.Goods;
import com.shoes.scarecrow.persistence.domain.GoodsCondition;
import com.shoes.scarecrow.persistence.domain.GoodsDetail;
import com.shoes.scarecrow.persistence.service.GoodsService;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-4-24
 * Time 下午9:32
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static Logger log = Logger.getLogger(BrandController.class);

    @Autowired
    private GoodsService goodsService;
    @RequestMapping("/allDetail")
    @ResponseBody
    public Map<String, Object> getBrandDetailByPage(int page, int limit, HttpSession session, HttpServletResponse response) {
        limit = limit<0?10:limit;
        log.info(session.getAttribute("userName") + "进入到分页获取商品信息的方法，limit=" + limit + ",page=" + page);
        Map<String,Object> map = new HashMap<>();
        GoodsCondition goodsCondition = new GoodsCondition();
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        goodsCondition.setUserId(userId);
        goodsCondition.setPage(null);
        goodsCondition.setStartRow(null);
        goodsCondition.setPageSize(null);
        int total = goodsService.queryCountByCondition(goodsCondition);
        goodsCondition.setPage(page);
        goodsCondition.setPageSize(limit);
        int start = (page-1)*limit;
        goodsCondition.setStartRow(start);
        List<Goods> list = goodsService.queryByCondition(goodsCondition);
        ObjectMapper mapper = new ObjectMapper();
        map.put("code","0");
        map.put("msg","");
        map.put("count",total);
        map.put("data",list);
        try {
            log.info(session.getAttribute("userName")+"退出分页获取商品信息的方法，result="+mapper.writeValueAsString(map));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("/deleteType/{id}")
    @ResponseBody
    public Map deleteGoodsById(@PathVariable("id") Integer goodId,HttpServletResponse response,HttpSession session){
        log.info(session.getAttribute("userName") + "进入到删除商品信息的方法，删除商品id="+goodId);
        Map<String,Object> map = new HashMap<>();
        Goods goods = new Goods();
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        goods.setUserId(userId);
        goods.setId(goodId);
        String userName = String.valueOf(session.getAttribute("userName"));
        goods.setUpdateUser(userName);
        goods.setUpdateTime(new Date());
        goods.setYn(0);
        int n = goodsService.deleteByUserIdAndId(goods);
        if(n > 0 ){
            map.put("success",true);
        }else{
            map.put("success",false);
        }
        return map;
    }
    @RequestMapping("/updateGoods")
    @ResponseBody
    public Map putGoods(Goods goods,HttpSession session){
        Map<String,Object> map = new HashMap<>();
        ObjectMapper objMapper = new ObjectMapper();
        try {
            log.info(session.getAttribute("userName")+"进入修改商品信息方法,修改的商品信息="+objMapper.writeValueAsString(goods));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        goods.setUserId(userId);
        String userName = String.valueOf(session.getAttribute("userName"));
        goods.setUpdateUser(userName);
        goods.setUpdateTime(new Date());
        int n = goodsService.updateGoods(goods);
        if(n > 0 ){
            map.put("success",true);
        }else{
            map.put("success",false);
        }
        try {
            log.info(session.getAttribute("userName")+"离开修改商品信息方法,修改的商品信息="+objMapper.writeValueAsString(goods));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map ;
    }
    @RequestMapping("/addGoods")
    @ResponseBody
    public Map postGoods(Goods goods, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        ObjectMapper objMapper = new ObjectMapper();
        String userId = String.valueOf(session.getAttribute("userId"));
        String userName = String.valueOf(session.getAttribute("userName"));
        goods.setUserId(Integer.valueOf(userId));
        goods.setYn(1);
        goods.setCreateTime(new Date());
        goods.setCreateUser(userName);
        try {
            log.info(session.getAttribute("userName")+"进入添加商品信息方法,添加的商品信息="+objMapper.writeValueAsString(goods));
        } catch (IOException e) {
            e.printStackTrace();
        }
        GoodsCondition condition = new GoodsCondition();
        condition.setUserId(Integer.valueOf(userId));
        condition.setPage(null);
        condition.setStartRow(null);
        condition.setPageSize(null);
        condition.setGoodsName(goods.getGoodsName());
        List<Goods> list = goodsService.queryByCondition(condition);
        if(list.size()>0){
            map.put("flag",false);
            map.put("message","该商品名称已经存在！");
        }else{
            int n = goodsService.saveGoods(goods);
            if(n > 0 ){
                map.put("flag",true);
                map.put("message","添加成功！");
            }else{
                map.put("flag",false);
                map.put("message","添加失败！联系管理员");
            }
        }
        try {
            log.info(session.getAttribute("userName")+"离开添加商品信息方法,添加的商品信息="+objMapper.writeValueAsString(goods));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map ;
    }
    @RequestMapping("/getGoodsIdAndName")
    @ResponseBody
    public Map getGoodsIdAndName(HttpSession session){
        log.info(session.getAttribute("userName")+"进入查找商品name and id信息方法");
        Map<String,Object> map = new HashMap<>();
        GoodsCondition goodsCondition = new GoodsCondition();
        goodsCondition.setPageSize(null);
        goodsCondition.setPage(null);
        goodsCondition.setStartRow(null);
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        goodsCondition.setUserId(userId);
        List<Goods> list = goodsService.queryByCondition(goodsCondition);
        map.put("data",list);
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info(session.getAttribute("userName")+"离开查找商品name and id信息方法,list="+mapper.writeValueAsString(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping("/getGoods")
    @ResponseBody
    public Map getGoods(int page, int limit, GoodsCondition goodsCondition,String goodsSize,HttpSession session){
        limit = limit<0?10:limit;
        Map<String,Object> map = new HashMap<>();
        ObjectMapper objMapper = new ObjectMapper();
        try {
            log.info(session.getAttribute("userName")+"进入查找商品信息方法,查找的商品信息="+objMapper.writeValueAsString(goodsCondition));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        goodsCondition.setUserId(userId);
        goodsCondition.setPage(null);
        goodsCondition.setStartRow(null);
        goodsCondition.setPageSize(null);
        int total = goodsService.queryCountByCondition(goodsCondition);
        goodsCondition.setPage(page);
        goodsCondition.setPageSize(limit);
        int start = (page-1)*limit;
        goodsCondition.setStartRow(start);
        List<Goods> list = goodsService.queryByCondition(goodsCondition);
        map.put("code","0");
        map.put("msg","");
        map.put("count",list.size());
        map.put("data",list);
        try {
            log.info(session.getAttribute("userName")+"离开查找商品信息方法,查找的商品信息="+objMapper.writeValueAsString(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map ;
    }
    @RequestMapping("/getOthersGoods")
    @ResponseBody
    public Map getOhtersGoods(int page,int limit,GoodsCondition goodsCondition,HttpSession session){
        limit = limit<0?10:limit;
        Map<String,Object> map = new HashMap<>();
        ObjectMapper objMapper = new ObjectMapper();
        try {
            log.info(session.getAttribute("userName")+"进入查找其他用户共享商品信息方法,查找的商品信息="+objMapper.writeValueAsString(goodsCondition));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        goodsCondition.setUserId(userId);
        goodsCondition.setPage(null);
        goodsCondition.setStartRow(null);
        goodsCondition.setPageSize(null);
        int total = goodsService.queryCountByBuyerCondition(goodsCondition);
        goodsCondition.setPage(page);
        goodsCondition.setPageSize(limit);
        int start = (page-1)*limit;
        goodsCondition.setStartRow(start);
        List<GoodsDetail> list = goodsService.queryByBuyerCondition(goodsCondition);
        map.put("code","0");
        map.put("msg","");
        map.put("count",list.size());
        map.put("data",list);
        try {
            log.info(session.getAttribute("userName")+"离开查找其他用户共享商品信息方法,查找的商品信息="+objMapper.writeValueAsString(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map ;
    }
}
