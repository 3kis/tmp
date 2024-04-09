package com.thinkstu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinkstu.common.MyCheckException;
import com.thinkstu.entity.Category;
import com.thinkstu.entity.Dish;
import com.thinkstu.entity.Setmeal;
import com.thinkstu.mapper.CategoryMapper;
import com.thinkstu.service.CategoryService;
import com.thinkstu.service.DishService;
import com.thinkstu.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜品及套餐分类(Category)表服务实现类
 *
 * @author asher
 * @since 2024-04-14 12:06:36
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;

    @Override
    public void check(Long id) {
        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
        dishWrapper.eq(Dish::getCategoryId, id);
        long countDish = dishService.count(dishWrapper);
        if (countDish > 0) {
            throw new MyCheckException("数据存在关联，无法删除！");
        }
        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        setmealWrapper.eq(Setmeal::getCategoryId, id);
        long countSetmeal = setmealService.count(setmealWrapper);
        if (countSetmeal > 0) {
            throw new MyCheckException("数据存在关联，无法删除！");
        }
    }
}