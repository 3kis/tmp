package com.thinkstu.dto;

import com.thinkstu.entity.*;
import lombok.*;

import java.util.*;

@Data
public class SetmealDTO extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
