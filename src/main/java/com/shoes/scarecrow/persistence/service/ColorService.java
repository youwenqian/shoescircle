package com.shoes.scarecrow.persistence.service;

import com.shoes.scarecrow.persistence.domain.Color;
import com.shoes.scarecrow.persistence.mappers.ColorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-5-3
 * Time 下午6:47
 */
@Service("colorService")
public class ColorService {
    @Autowired
    ColorMapper colorMapper;
    public List<Color> queryAllColor() {
        return colorMapper.getAllColor();
    }
}
