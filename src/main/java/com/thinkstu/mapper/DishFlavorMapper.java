package com.thinkstu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinkstu.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品口味关系表(DishFlavor)表数据库访问层
 *
 * @author asher
 * @since 2023-04-15 10:45:26
 */
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}