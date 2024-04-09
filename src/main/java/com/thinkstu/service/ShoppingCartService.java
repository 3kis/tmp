package com.thinkstu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.thinkstu.entity.ShoppingCart;

/**
 * 购物车(ShoppingCart)表服务接口
 *
 * @author asher
 * @since 2024-04-26 10:33:12
 */
public interface ShoppingCartService extends IService<ShoppingCart> {
    ShoppingCart add(ShoppingCart cart);

    ShoppingCart substract(ShoppingCart cart);
}