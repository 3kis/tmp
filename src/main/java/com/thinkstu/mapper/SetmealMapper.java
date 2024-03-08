package com.thinkstu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinkstu.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

/**
 * 套餐(Setmeal)表数据库访问层
 *
 * @author asher
 * @since 2023-04-14 16:06:16
 */
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
}