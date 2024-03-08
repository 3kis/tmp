package com.thinkstu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinkstu.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品管理(Dish)表数据库访问层
 *
 * @author asher
 * @since 2023-04-14 16:05:33
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}