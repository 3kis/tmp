package com.thinkstu.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thinkstu.dto.DishDTO;
import com.thinkstu.entity.Dish;

import java.util.List;

/**
 * 菜品管理(Dish)表服务接口
 *
 * @author asher
 * @since 2023-04-14 16:05:35
 */
public interface DishService extends IService<Dish> {
    void add(DishDTO dishDto);

    Page<DishDTO> myPage(Long page, Long pageSize, String name);

    void delete(List<Long> ids);

    List<DishDTO> getBatchDish(Long categoryId);

    DishDTO getSingle(Long id);

    void myUpdate(DishDTO dto);
}