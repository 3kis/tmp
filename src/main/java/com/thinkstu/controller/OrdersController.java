package com.thinkstu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.*;
import com.thinkstu.common.*;
import com.thinkstu.dto.*;
import com.thinkstu.entity.*;
import com.thinkstu.service.impl.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * 订单表(Orders)表控制层
 *
 * @author thinkstu
 * @since 2023-04-26 13:40:52
 */
@Api(tags = "订单系统")
@RestController
@RequestMapping("order")
public class OrdersController {
    @Autowired
    OrdersServiceImpl service;

    @PostMapping("submit")
    @ApiOperation("下单订单")
    R<String> submit(@RequestBody Orders orders) {
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