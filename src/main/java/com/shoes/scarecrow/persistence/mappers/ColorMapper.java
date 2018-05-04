package com.shoes.scarecrow.persistence.mappers;

import com.shoes.scarecrow.persistence.domain.Color;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create with IntelliJ IDEA
 * Create by zz
 * Date 18-5-3
 * Time 下午6:48
 */
@Repository("colorMapper")
public interface ColorMapper {

    public List<Color> getAllColor();
}
