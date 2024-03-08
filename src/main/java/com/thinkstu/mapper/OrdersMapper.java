package com.thinkstu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinkstu.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表(Orders)表数据库访问层
 *
 * @author asher
 * @since 2023-04-26 13:40:52
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}