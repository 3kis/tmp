package com.thinkstu.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.*;
import com.baomidou.mybatisplus.extension.service.*;
import com.thinkstu.dto.*;
import com.thinkstu.entity.*;

import java.util.*;

/**
 * 套餐(Setmeal)表服务接口
 *
 * @author thinkstu
 * @since 2023-04-14 16:06:16
 */
public interface SetmealService extends IService<Setmeal> {
    void add(SetmealDTO setmealDto);

    Page<SetmealDTO> myPage(Long page, Long pageSize, String name);

    SetmealDTO getSingle(Long id);

    void myUpdate(SetmealDTO setmealDto);

    void delete(List<Long> ids);

    List<Dish> detailes(Long id);
}