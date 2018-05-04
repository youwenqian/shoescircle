package com.shoes.scarecrow.web.controller;

import com.shoes.scarecrow.persistence.domain.Color;
import com.shoes.scarecrow.persistence.service.ColorService;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-5-3
 * Time 下午6:40
 */
@Controller
@RequestMapping("/color")
public class ColorController {
    private Logger log = Logger.getLogger(TypeController.class);
    @Autowired
    private ColorService colorService;

    @RequestMapping("/getColor")
    @ResponseBody
    public Map getColor(HttpSession session){
        log.info(session.getAttribute("userName")+"进入到分页获取所有颜色信息的方法");
        Map<String,Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        List<Color> list = colorService.queryAllColor();
        map.put("code","0");
        map.put("msg","");
        map.put("count",list.size());
        map.put("data",list);
        try {
            log.info(session.getAttribute("userName")+"退出分页获取所有分类信息的方法，result="+mapper.writeValueAsString(map));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
