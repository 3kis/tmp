package com.thinkstu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinkstu.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * 购物车(ShoppingCart)表数据库访问层
 *
 * @author asher
 * @since 2023-04-26 10:33:12
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}