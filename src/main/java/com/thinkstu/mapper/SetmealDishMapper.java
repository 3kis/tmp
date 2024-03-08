package com.thinkstu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinkstu.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

/**
 * 套餐菜品关系(SetmealDish)表数据库访问层
 *
 * @author asher
 * @since 2023-04-15 21:19:39
 */
@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {
}