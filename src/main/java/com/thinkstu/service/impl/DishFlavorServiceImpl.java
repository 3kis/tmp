package com.thinkstu.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinkstu.entity.DishFlavor;
import com.thinkstu.mapper.DishFlavorMapper;
import com.thinkstu.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * 菜品口味关系表(DishFlavor)表服务实现类
 *
 * @author asher
 * @since 2024-04-15 10:45:26
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}