package com.thinkstu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinkstu.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜品及套餐分类(Category)表数据库访问层
 *
 * @author asher
 * @since 2024-04-14 12:09:37
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}