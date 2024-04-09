package com.thinkstu.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinkstu.entity.SetmealDish;
import com.thinkstu.mapper.SetmealDishMapper;
import com.thinkstu.service.SetmealDishService;
import org.springframework.stereotype.Service;

/**
 * 套餐菜品关系(SetmealDish)表服务实现类
 *
 * @author asher
 * @since 2024-04-15 21:19:39
 */
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}