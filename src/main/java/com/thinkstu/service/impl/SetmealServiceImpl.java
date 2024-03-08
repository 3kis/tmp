package com.thinkstu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.*;
import com.baomidou.mybatisplus.extension.service.impl.*;
import com.thinkstu.common.*;
import com.thinkstu.dto.*;
import com.thinkstu.entity.*;
import com.thinkstu.mapper.*;
import com.thinkstu.service.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

/**
 * 套餐(Setmeal)表服务实现类
 *
 * @author thinkstu
 * @since 2023-04-14 16:06:16
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    SetmealDishService setmealDishService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    DishService dishService;

    @Override
    public void add(SetmealDTO setmealDto) {
        this.save(setmealDto);
        Long id = setmealDto.getId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(id);
            setmealDishService.save(setmealDish);
        }
    }

    // 分页
    @Override
    @Transactional
    public Page<SetmealDTO> myPage(Long page, Long pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        Page<SetmealDTO> setmealDtoPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Setmeal::getName, name);
        page(setmealPage, wrapper);
        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");
        List<Setmeal> setmeals = setmealPage.getRecords();
        ArrayList<SetmealDTO> setmealDTOS = new ArrayList<>();
        for (Setmeal setmeal : setmeals) {
            String categoryName = null;
            SetmealDTO setmealDto = new SetmealDTO();
            BeanUtils.copyProperties(setmeal, setmealDto);
            Long categoryId = setmeal.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                categoryName = category.getName();
            }
            setmealDto.setCategoryName(categoryName);
            setmealDTOS.add(setmealDto);
        }
        setmealDtoPage.setRecords(setmealDTOS);
        return setmealDtoPage;
    }

    @Override
    public SetmealDTO getSingle(Long id) {
        SetmealDTO setmealDto = new SetmealDTO();
        Setmeal setmeal = getById(id);
        BeanUtils.copyProperties(setmeal, setmealDto);
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> setmealDishList = setmealDishService.list(wrapper);
        setmealDto.setSetmealDishes(setmealDishList);
        return setmealDto;
    }

    @Override
    public void myUpdate(SetmealDTO setmealDto) {
        updateById(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        Long id = setmealDto.getId();
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId, id);
        setmealDishService.remove(wrapper);
        setmealDishes.forEach(e -> {
            e.setSetmealId(id);
        });
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Setmeal::getId, ids).eq(Setmeal::getStatus, 1);
        long count = count(wrapper);
        if (count > 0) {
            throw new MyCheckException("套餐正在售卖，不能删除！");
        }
        removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> wrap = new LambdaQueryWrapper<>();
        wrap.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(wrap);
    }

    @Override
    public List<Dish> detailes(Long id) {
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(id != null, SetmealDish::getSetmealId, id);
        List<SetmealDish> setmealDishes = setmealDishService.list(wrapper);
        List<Dish> dishes = new ArrayList<>();
        for (SetmealDish setmealDish : setmealDishes) {
            Dish dish = dishService.getById(setmealDish.getDishId());
            dishes.add(dish);
        }
        return dishes;
    }
}