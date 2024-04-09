package com.thinkstu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thinkstu.common.R;
import com.thinkstu.dto.OrdersDTO;
import com.thinkstu.entity.Orders;
import com.thinkstu.service.impl.OrdersServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单表(Orders)表控制层
 *
 * @author asher
 * @since 2024-04-26 13:40:52
 */
@Api(tags = "订单系统")
@RestController
@RequestMapping("order")
public class OrdersController {

    Map<Long, Boolean> existOrderMap = new HashMap<>();

    @Autowired
    OrdersServiceImpl service;

    @PostMapping("submit")
    @ApiOperation("下单订单")
    R<String> submit(@RequestBody Orders orders) {
        if (existOrderMap.containsKey(orders.getId())) return R.success("下单成功");
        else existOrderMap.put(orders.getId(), true);

        service.order(orders);
        return R.success("下单成功~");
    }

    /**
     * 复杂的分页操作
     *
     * @param page      页码
     * @param pageSize  页容量
     * @param number    订单编号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return 分页数据
     */
    @GetMapping(value = {"userPage", "page"})
    @ApiOperation("分页查询")
    R<Page<OrdersDTO>> pages(Long page, Long pageSize, Long number, String beginTime, String endTime) {
        return R.success(service.myPages(page, pageSize, number, beginTime, endTime));
    }

    @PutMapping
    @ApiOperation("更新订单")
    R<String> update(@RequestBody Orders orders) {
        service.updateById(orders);
        return R.success("成功~");
    }

    @PostMapping("again")
    @ApiOperation("再来一单")
    R<String> again(@RequestBody Orders id) {
        service.again(id);
        return R.success("再来一单~");
    }

}