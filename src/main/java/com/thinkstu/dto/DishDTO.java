package com.thinkstu.dto;

import com.thinkstu.entity.Dish;
import com.thinkstu.entity.DishFlavor;
import lombok.Data;

import java.util.List;

/**
 * @author : Asher
 * @since : 2024-04/15, 3:04 PM, 周日
 **/
@Data
public class DishDTO extends Dish {
    private List<DishFlavor> flavors;
    private String categoryName;
}
