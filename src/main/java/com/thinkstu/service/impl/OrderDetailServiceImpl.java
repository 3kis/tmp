package com.thinkstu.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinkstu.entity.OrderDetail;
import com.thinkstu.mapper.OrderDetailMapper;
import com.thinkstu.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * 订单明细表(OrderDetail)表服务实现类
 *
 * @author asher
 * @since 2023-04-26 13:40:52
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}