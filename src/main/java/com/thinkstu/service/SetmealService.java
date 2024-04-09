package com.thinkstu.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thinkstu.dto.SetmealDTO;
import com.thinkstu.entity.Dish;
import com.thinkstu.entity.Setmeal;

import java.util.List;

/**
 * 套餐(Setmeal)表服务接口
 *
 * @author asher
 * @since 2024-04-14 16:06:16
 */
public interface SetmealService extends IService<Setmeal> {
    void add(SetmealDTO setmealDto);

    Page<SetmealDTO> myPage(Long page, Long pageSize, String name);

    SetmealDTO getSingle(Long id);

    void myUpdate(SetmealDTO setmealDto);

    void delete(List<Long> ids);

    List<Dish> detailes(Long id);
}