package com.thinkstu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinkstu.common.MyCheckException;
import com.thinkstu.dto.DishDTO;
import com.thinkstu.entity.Category;
import com.thinkstu.entity.Dish;
import com.thinkstu.entity.DishFlavor;
import com.thinkstu.mapper.DishMapper;
import com.thinkstu.service.CategoryService;
import com.thinkstu.service.DishFlavorService;
import com.thinkstu.service.DishService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜品管理(Dish)表服务实现类
 *
 * @author asher
 * @since 2023-04-14 16:05:36
 */
@Service

public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    DishFlavorService flavorService;
    @Autowired
    CategoryService categoryService;

    @Override
    @Transactional
    public void add(DishDTO dishDto) {
        this.save(dishDto);
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        // 为 flavors 设置菜品id
        flavors.forEach(e -> {
            e.setDishId(dishId);
        });
        flavorService.saveBatch(flavors);
    }

    // 分页
    @Override
    @Transactional
    public Page<DishDTO> myPage(Long page, Long pageSize, String name) {
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDTO> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name)
                .orderByAsc(Dish::getSort)
                .orderByDesc(Dish::getUpdateTime);
        page(dishPage, wrapper);
        // 拷贝 page基础属性，不拷贝数据（否则会出错！）。
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");
        // 下面开始拷贝数据
        List<Dish> dishList = dishPage.getRecords();
        List<DishDTO> dishDTOList = new ArrayList<>();
        DishDTO dishDto;
        for (Dish dish : dishList) {
            dishDto = new DishDTO();
            BeanUtils.copyProperties(dish, dishDto);
            Category category = categoryService.getById(dish.getCategoryId());
            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }
            dishDTOList.add(dishDto);
        }
        dishDtoPage.setRecords(dishDTOList);
        return dishDtoPage;
    }

    // 删除
    @Override
    public void delete(List<Long> ids) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Dish::getId, ids).eq(Dish::getStatus, 1);
        long count = count(wrapper);
        if (count > 0) {
            throw new MyCheckException("商品正在销售，无法删除！");
        }
        removeByIds(ids);
    }

    @Override
    public List<DishDTO> getBatchDish(Long categoryId) {
        List<DishDTO> dtos = new ArrayList<>();

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(categoryId != null, Dish::getCategoryId, categoryId).eq(Dish::getStatus, 1)
                .orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> dishList = list(wrapper);
        DishDTO dishDto;
        // 为菜品分别设置类别、口味
        for (Dish dish : dishList) {
            dishDto = new DishDTO();
            BeanUtils.copyProperties(dish, dishDto);
            Category category = categoryService.getById(dish.getCategoryId());
            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }
            LambdaQueryWrapper<DishFlavor> dishWrapper = new LambdaQueryWrapper<>();
            dishWrapper.eq(DishFlavor::getDishId, dish.getId());
            List<DishFlavor> flavors = flavorService.list(dishWrapper);
            if (flavors != null) {
                dishDto.setFlavors(flavors);
            }
            dtos.add(dishDto);
        }
        return dtos;
    }

    @Override
    public DishDTO getSingle(Long id) {
        Dish dish = getById(id);
        DishDTO dto = new DishDTO();
        BeanUtils.copyProperties(dish, dto);
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, id);
        List<DishFlavor> flavors = flavorService.list(wrapper);
        dto.setFlavors(flavors);
        return dto;
    }

    @Override
    public void myUpdate(DishDTO dto) {
        updateById(dto);
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dto.getId());
        flavorService.remove(wrapper);
        List<DishFlavor> flavors = dto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dto.getId());
        }
        flavorService.saveBatch(flavors);
    }
}

