package com.shoes.scarecrow.web.controller;

import com.shoes.scarecrow.common.utils.DateUtils;
import com.shoes.scarecrow.persistence.domain.*;
import com.shoes.scarecrow.persistence.service.GoodsExtendService;
import com.shoes.scarecrow.persistence.service.StockFlowService;
import com.shoes.scarecrow.persistence.service.StockService;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-5-3
 * Time 下午11:17
 */
@Controller
@RequestMapping("/stock")
public class StockController {
    private Logger log = Logger.getLogger(StockController.class);
    @Autowired
    private StockService stockService;
    @Autowired
    private GoodsExtendService goodsExtendService;
    @Autowired
    private StockFlowService stockFlowService;

    @RequestMapping("/getStockDetail")
    @ResponseBody
    public Map getStockDetailByPage(int page, int limit, StockCondition condition, HttpSession session) {
        limit = limit<0?10:limit;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(session.getAttribute("userName") + "进入查询库存方法,page=" + page + " ,limit=" + limit + " " + objectMapper.writeValueAsString(condition));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        condition.setUserId(userId);
        condition.setPage(null);
        condition.setStartRow(null);
        condition.setPageSize(null);
        int total = stockService.getStockDetailCountByCondition(condition);
        List<StockDetail> list = new ArrayList<>();
        if (total > 0) {
            list = stockService.getStockDetailByCondition(condition);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", "0");
        map.put("msg", "");
        map.put("count", total);
        map.put("data", list);
        try {
            log.info(session.getAttribute("userName") + "离开查询库存方法,page=" + page + " ,limit=" + limit + " " + objectMapper.writeValueAsString(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("/addStock")
    @ResponseBody
    public Map addStock(Stock stock,Integer colorId,Integer sizeId, HttpSession session) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(session.getAttribute("userName") + "进入添加库存信息方法,colorId="+colorId+",sizeId="+sizeId+"," + objectMapper.writeValueAsString(stock));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        String userName = String.valueOf(session.getAttribute("userName"));
        stock.setCreateUser(userName);
        stock.setUserId(userId);
        stock.setCreateUser(userName);
        stock.setCreateTime(new Date());
        stock.setYn(1);
        Map<String,Object> map = new HashMap<>();
        int n =0;
        GoodExtend goodExtend = new GoodExtend();
        goodExtend.setGoodId(stock.getGoodsId());
        goodExtend.setColorId(colorId);
        goodExtend.setSizeId(sizeId);
        List<GoodExtend> list = goodsExtendService.getGoodExtend(goodExtend);
        if(list.size()>0){
            stock.setGoodsExtendId(list.get(0).getExtendId());
        }else{
            n = goodsExtendService.saveGoodExtend(goodExtend);
            stock.setGoodsExtendId(goodExtend.getExtendId());
        }
        n = stockService.saveStock(stock);
        if (n > 0) {
            map.put("flag", true);
            map.put("message", "添加成功！");
        } else {
            map.put("flag", false);
            map.put("message", "添加失败，请联系管理员！");
        }
        try {
            log.info(session.getAttribute("userName") + "离开添加库存信息方法," + objectMapper.writeValueAsString(stock));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("/updateStock")
    @ResponseBody
    public Map updateStock(Stock stock,Integer sizeId,Integer colorId, HttpSession session) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(session.getAttribute("userName") + "进入更新库存信息方法,sizeId="+sizeId+",colorId="+colorId+"," + objectMapper.writeValueAsString(stock));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        String userName = String.valueOf(session.getAttribute("userName"));
        stock.setUpdateUser(userName);
        stock.setUpdateTime(new Date());
        stock.setUserId(userId);
        Map<String,Object> map = new HashMap<>();
        int n = 0;
        GoodExtend goodExtend = new GoodExtend();
        goodExtend.setGoodId(stock.getGoodsId());
        goodExtend.setColorId(colorId);
        goodExtend.setSizeId(sizeId);
        List<GoodExtend> list = goodsExtendService.getGoodExtend(goodExtend);
        if(list.size()>0){
            stock.setGoodsExtendId(list.get(0).getExtendId());
        }else{
            n = goodsExtendService.saveGoodExtend(goodExtend);
            stock.setGoodsExtendId(goodExtend.getExtendId());
        }
        n = stockService.updateStock(stock);
        if (n > 0) {
            map.put("flag", true);
            map.put("message", "更新成功！");
        } else {
            map.put("flag", false);
            map.put("message", "更新失败，请联系管理员！");
        }
        try {
            log.info(session.getAttribute("userName") + "离开更新库存信息方法,sizeId="+sizeId+",colorId="+colorId+","  + objectMapper.writeValueAsString(stock));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("/outStock")
    @ResponseBody
    public Map outStock(HttpSession session,Long goodsQty, StockFlow stockFlow){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(session.getAttribute("userName") + "进入更新出库信息方法,库存数量="+goodsQty+","+ objectMapper.writeValueAsString(stockFlow));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stockFlow.setCreateTime(new Date());
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        stockFlow.setUserId(userId);
        stockFlow.setFlowType("0");
        stockFlow.setStockDate(new Date());
        int n =0;
        n = stockFlowService.saveStockFlow(stockFlow);
        //更新库存数量
        Long leftover = goodsQty-stockFlow.getNum();
        Stock stock = new Stock();
        String userName = String.valueOf(session.getAttribute("userName"));
        stock.setUpdateUser(userName);
        stock.setUpdateTime(new Date());
        if(leftover.intValue()<=0){
            stock.setStatus(0);//售完
        }else{
            stock.setStatus(1);
        }
        stock.setQty(leftover.intValue());
        stock.setUserId(userId);
        stock.setId(stockFlow.getStockId());
        n = stockService.updateStock(stock);
        Map<String,Object> map = new HashMap<>();
        if(n>0){
            map.put("flag",true);
        }else{
            map.put("flag",false);
        }
        try {
            log.info(session.getAttribute("userName") + "离开更新出库信息方法"+ objectMapper.writeValueAsString(stockFlow));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 查询最近七天的出库金额
     * @param session
     * @return
     */
    @RequestMapping("/getStockFlowSevenOut")
    @ResponseBody
    public Map getStockFlowSevenOut(HttpSession session,Integer days){
        log.info(session.getAttribute("userName")+"进入到查询最近7天出库金额方法");
        Map<String,Object> map = new HashMap<>();
        if(days==null||days.intValue()==0){
            days = 7;
        }
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        List<Integer> data = new ArrayList<>();
        List<DayMoney> list = stockFlowService.getSevenOutStockFlow(userId,days);
        List<String> dataAxis = DateUtils.getLastDaysByPatten("MM-dd",days);
        if(list.size()>0){
            if(list.size()==dataAxis.size()){//都是七天的数据
                if(list.get(list.size()-1).getCreateTime().equals(dataAxis.get(dataAxis.size()-1))){//数据库中获取的7天和java程序中获取的7天都是同一天
                    for(int i=0;i<list.size();i++){
                        data.add(list.get(i).getTotal());
                    }
                }else{//数据库中获取的7天和java程序中获取的7天 不 都是同一天
                    String monthDay = list.get(list.size()-1).getCreateTime();
                    String[] cols = monthDay.split("-");
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.MONTH,Integer.valueOf(cols[0])-1);
                    calendar.set(Calendar.DATE,Integer.valueOf(cols[1])+1);
                    Date date = calendar.getTime();
                    dataAxis = DateUtils.getLastDaysByPatten(date,"MM-dd",days);
                    for(int i=0;i<list.size();i++){
                        data.add(list.get(i).getTotal());
                    }
                }
            }else{//数据库中的数据比七天少
                if(list.get(list.size()-1).getCreateTime().equals(dataAxis.get(dataAxis.size()-1))){//都有最近的数据
                    for(int i=0,j=0;i<dataAxis.size()&&j<list.size();i++){
                        if(dataAxis.get(i).equals(list.get(j).getCreateTime())){
                            data.add(list.get(j).getTotal());
                            j++;
                        }else{
                            data.add(0);
                        }
                    }
                }else{//数据库中没有最近的数据
                    if(list.get(0).getCreateTime().equals(dataAxis.get(0))){//最久的数据
                        for(int i=0,j=0;i<dataAxis.size()&&j<list.size();i++){
                            if(dataAxis.get(i).equals(list.get(j).getCreateTime())){
                                data.add(list.get(j).getTotal());
                                j++;
                            }else{
                                data.add(0);
                            }
                        }
                    }else{
                        String monthDay = list.get(0).getCreateTime();
                        String[] cols = monthDay.split("-");
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH,Integer.valueOf(cols[0])-1);
                        calendar.set(Calendar.DATE,Integer.valueOf(cols[1])+1+days);
                        Date date = calendar.getTime();
                        dataAxis = DateUtils.getLastDaysByPatten(date,"MM-dd",days);
                        for(int i=0,j=0;i<dataAxis.size()&&j<list.size();i++){
                            if(dataAxis.get(i).equals(list.get(j).getCreateTime())){
                                data.add(list.get(j).getTotal());
                                j++;
                            }else{
                                data.add(0);
                            }
                        }
                    }
                }
            }
        }else{
           for(int i=0;i<dataAxis.size();i++){
               data.add(0);
           }
        }
        map.put("dataAxis",dataAxis);
        map.put("data",data);
        log.info(session.getAttribute("userName")+"离开查询最近7天出库金额方法");
        return map;
    }

    @RequestMapping("/getSevenOutBills")
    @ResponseBody
    public Map getSevenOutBills(HttpSession session,Integer days){
        log.info(session.getAttribute("userName")+"进入到查询最近7天开单数方法");
        Map<String,Object> map = new HashMap<>();
        if(days==null||days.intValue()==0){
            days = 7;
        }
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        List<Integer> data = new ArrayList<>();
        List<DayBills> list = stockFlowService.getSevenOutbillsGroupDay(userId,days);
        List<String> dataAxis = DateUtils.getLastDaysByPatten("MM-dd",days);
        if(list.size()>0){
            if(list.size()==dataAxis.size()){//都是七天的数据
                if(list.get(list.size()-1).getName().equals(dataAxis.get(dataAxis.size()-1))){//数据库中获取的7天和java程序中获取的7天都是同一天
                }else{//数据库中获取的7天和java程序中获取的7天 不 都是同一天
                    String monthDay = list.get(list.size()-1).getName();
                    String[] cols = monthDay.split("-");
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.MONTH,Integer.valueOf(cols[0])-1);
                    calendar.set(Calendar.DATE,Integer.valueOf(cols[1])+1);
                    Date date = calendar.getTime();
                    dataAxis = DateUtils.getLastDaysByPatten(date,"MM-dd",days);
                    for(int i=0,j=0;i<dataAxis.size();i++,j++){
                        if(!dataAxis.get(i).equals(list.get(j).getName())){
                            DayBills dayBill = new DayBills();
                            dayBill.setName(dataAxis.get(i));
                            dayBill.setValue(0);
                            list.add(j,dayBill);
                        }
                    }
                }
            }else{//数据库中的数据比七天少
                if(list.get(list.size()-1).getName().equals(dataAxis.get(dataAxis.size()-1))){//都有最近的数据
                    for(int i=0,j=0;i<dataAxis.size();i++,j++){
                        if(!dataAxis.get(i).equals(list.get(j).getName())){
                            DayBills dayBill = new DayBills();
                            dayBill.setName(dataAxis.get(i));
                            dayBill.setValue(0);
                            list.add(j,dayBill);
                        }
                    }
                }else{//数据库中没有最近的数据
                    if(list.get(0).getName().equals(dataAxis.get(0))){//最久的数据
                        for(int i=0,j=0;i<dataAxis.size();i++,j++){
                            if(!dataAxis.get(i).equals(list.get(j).getName())){
                                DayBills dayBill = new DayBills();
                                dayBill.setName(dataAxis.get(i));
                                dayBill.setValue(0);
                                list.add(j,dayBill);
                            }
                        }
                    }else{
                        String monthDay = list.get(0).getName();
                        String[] cols = monthDay.split("-");
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH,Integer.valueOf(cols[0])-1);
                        calendar.set(Calendar.DATE,Integer.valueOf(cols[1])+1+days);
                        Date date = calendar.getTime();
                        dataAxis = DateUtils.getLastDaysByPatten(date,"MM-dd",days);
                        for(int i=0,j=0;i<dataAxis.size();i++,j++){
                            if(!dataAxis.get(i).equals(list.get(j).getName())){
                                DayBills dayBill = new DayBills();
                                dayBill.setName(dataAxis.get(i));
                                dayBill.setValue(0);
                                list.add(j,dayBill);
                            }
                        }
                    }
                }
            }
        }else{
            for(int i=0;i<dataAxis.size();i++){
                DayBills dayBills = new DayBills();
                dayBills.setName(dataAxis.get(i));
                dayBills.setValue(0);
                list.add(dayBills);
            }
        }
        map.put("dataAxis",dataAxis);
        map.put("data",list);
        log.info(session.getAttribute("userName")+"离开查询最近7天开单数方法");
        return map;
    }
    @RequestMapping("/getStockFlowSevenIn")
    @ResponseBody
    public Map getStockFlowSevenIn(HttpSession session,Integer days){
        log.info(session.getAttribute("userName")+"进入到查询最近7天入库金额方法");
        Map<String,Object> map = new HashMap<>();
        if(days==null||days.intValue()==0){
            days = 7;
        }
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        List<Integer> data = new ArrayList<>();
        List<DayMoney> list = stockService.getSevenInStockFlow(userId,days);
        List<String> dataAxis = DateUtils.getLastDaysByPatten("MM-dd",days);
        if(list.size()>0){
            if(list.size()==dataAxis.size()){//都是七天的数据
                if(list.get(list.size()-1).getCreateTime().equals(dataAxis.get(dataAxis.size()-1))){//数据库中获取的7天和java程序中获取的7天都是同一天
                    for(int i=0;i<list.size();i++){
                        data.add(list.get(i).getTotal());
                    }
                }else{//数据库中获取的7天和java程序中获取的7天 不 都是同一天
                    String monthDay = list.get(list.size()-1).getCreateTime();
                    String[] cols = monthDay.split("-");
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.MONTH,Integer.valueOf(cols[0])-1);
                    calendar.set(Calendar.DATE,Integer.valueOf(cols[1])+1);
                    Date date = calendar.getTime();
                    dataAxis = DateUtils.getLastDaysByPatten(date,"MM-dd",days);
                    for(int i=0;i<list.size();i++){
                        data.add(list.get(i).getTotal());
                    }
                }
            }else{//数据库中的数据比七天少
                if(list.get(list.size()-1).getCreateTime().equals(dataAxis.get(dataAxis.size()-1))){//都有最近的数据
                    for(int i=0,j=0;i<dataAxis.size()&&j<list.size();i++){
                        if(dataAxis.get(i).equals(list.get(j).getCreateTime())){
                            data.add(list.get(j).getTotal());
                            j++;
                        }else{
                            data.add(0);
                        }
                    }
                }else{//数据库中没有最近的数据
                    if(list.get(0).getCreateTime().equals(dataAxis.get(0))){//最久的数据
                        for(int i=0,j=0;i<dataAxis.size()&&j<list.size();i++){
                            if(dataAxis.get(i).equals(list.get(j).getCreateTime())){
                                data.add(list.get(j).getTotal());
                                j++;
                            }else{
                                data.add(0);
                            }
                        }
                    }else{
                        String monthDay = list.get(0).getCreateTime();
                        String[] cols = monthDay.split("-");
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.MONTH,Integer.valueOf(cols[0])-1);
                        calendar.set(Calendar.DATE,Integer.valueOf(cols[1])+1+days);
                        Date date = calendar.getTime();
                        dataAxis = DateUtils.getLastDaysByPatten(date,"MM-dd",days);
                        for(int i=0,j=0;i<dataAxis.size()&&j<list.size();i++){
                            if(dataAxis.get(i).equals(list.get(j).getCreateTime())){
                                data.add(list.get(j).getTotal());
                                j++;
                            }else{
                                data.add(0);
                            }
                        }
                    }
                }
            }
        }else{
            for(int i=0;i<dataAxis.size();i++){
                data.add(0);
            }
        }
        map.put("dataAxis",dataAxis);
        map.put("data",data);
        log.info(session.getAttribute("userName")+"离开查询最近7天入库金额方法");
        return map;
    }

    @RequestMapping("/getStockIn")
    @ResponseBody
    public Map getStockIn(HttpSession session ){
        Map<String,Object> map = new HashMap<>();
        log.info(session.getAttribute("userName")+"进入到获取当天入库金额方法");
        String userId = String.valueOf(session.getAttribute("userId"));
        Integer value = stockService.getStockTodayPrice(userId);
        if(value==null){
            value = 0;
        }
        map.put("value",value);
        log.info(session.getAttribute("userName")+"进入到获取当天入库金额方法,入库金额="+value);
        return map;
    }

    @RequestMapping("/getStockOut")
    @ResponseBody
    public Map getStockOut(HttpSession session ){
        Map<String,Object> map = new HashMap<>();
        log.info(session.getAttribute("userName")+"进入到获取当天入库金额方法");
        String userId = String.valueOf(session.getAttribute("userId"));
        Integer value = stockFlowService.getStockFlowTodayPrice(userId);
        if(value==null){
            value = 0;
        }
        map.put("value",value);
        log.info(session.getAttribute("userName")+"进入到获取当天入库金额方法,入库金额="+value);
        return map;
    }

    @RequestMapping("/deleteStock/{id}")
    @ResponseBody
    public Map deleteStockById(@PathVariable("id") Integer id,HttpSession session){
        log.info(session.getAttribute("userName") + "进入到删除库存信息的方法，删除商品id="+id);
        Map<String,Object> map = new HashMap<>();
        Stock stock = new Stock();
        Integer userId = Integer.valueOf(String.valueOf(session.getAttribute("userId")));
        stock.setUserId(userId);
        stock.setId(id);
        String userName = String.valueOf(session.getAttribute("userName"));
        stock.setUpdateUser(userName);
        stock.setUpdateTime(new Date());
        stock.setYn(0);
        int n = stockService.deleteByUserIdAndId(stock);
        if(n > 0 ){
            map.put("success",true);
        }else{
            map.put("success",false);
        }
        log.info(session.getAttribute("userName") + "离开到删除库存信息的方法，删除商品id="+id);
        return map;
    }
}
