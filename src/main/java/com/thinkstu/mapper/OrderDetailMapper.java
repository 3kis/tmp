package com.thinkstu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinkstu.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单明细表(OrderDetail)表数据库访问层
 *
 * @author asher
 * @since 2023-04-26 13:40:52
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}