package com.thinkstu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.thinkstu.common.BaseContext;
import com.thinkstu.common.R;
import com.thinkstu.entity.ShoppingCart;
import com.thinkstu.service.impl.ShoppingCartServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车(ShoppingCart)表控制层
 *
 * @author asher
 * @since 2024-04-26 10:33:12
 */
@Api(tags = "购物车管理系统")
@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartServiceImpl service;

    @PostMapping("add")
    @ApiOperation("添加商品")
    R<ShoppingCart> add(@RequestBody ShoppingCart cart) {
        return R.success(service.add(cart));
    }

    @PostMapping("sub")
    @ApiOperation("删除商品")
    R<ShoppingCart> subtract(@RequestBody ShoppingCart cart) {
        return R.success(service.substract(cart));
    }

    @GetMapping("list")
    @ApiOperation("获取购物车列表信息")
    R<List<ShoppingCart>> list() {
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId).orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = service.list(wrapper);
        return R.success(list);
    }

    @DeleteMapping("clean")
    @ApiOperation("清空购物车")
    R<String> clean() {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        service.remove(wrapper);
        return R.success("清空购物车成功~");
    }
}