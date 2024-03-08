package com.thinkstu.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.thinkstu.dto.OrdersDTO;
import com.thinkstu.entity.Orders;

/**
 * 订单表(Orders)表服务接口
 *
 * @author asher
 * @since 2023-04-26 13:40:52
 */
public interface OrdersService extends IService<Orders> {
    void order(Orders orders);

    Page<OrdersDTO> myPages(Long page, Long pageSize, Long number, String beginTime, String endTime);

    void again(Orders id);
}