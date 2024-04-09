package com.thinkstu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinkstu.common.BaseContext;
import com.thinkstu.entity.ShoppingCart;
import com.thinkstu.mapper.ShoppingCartMapper;
import com.thinkstu.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 购物车(ShoppingCart)表服务实现类
 *
 * @author asher
 * @since 2024-04-26 10:33:12
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Override
    public ShoppingCart add(ShoppingCart cart) {
        Long userId = BaseContext.getCurrentId();
        cart.setUserId(userId);
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId)
                .eq(cart.getDishId() != null, ShoppingCart::getDishId, cart.getDishId())
                .eq(cart.getSetmealId() != null, ShoppingCart::getSetmealId, cart.getSetmealId());
        ShoppingCart one = getOne(wrapper);
        if (one != null) {
            one.setNumber(one.getNumber() + 1);
            updateById(one);
            return one;
        } else {
            cart.setNumber(1);
            cart.setCreateTime(LocalDateTime.now());
            save(cart);
            return cart;
        }
    }

    @Override
    public ShoppingCart substract(ShoppingCart cart) {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        Long dishId = cart.getDishId();
        Long setmealId = cart.getSetmealId();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId())
                .eq(dishId != null, ShoppingCart::getDishId, dishId)
                .eq(setmealId != null, ShoppingCart::getSetmealId, setmealId);
        cart = getOne(wrapper);
        cart.setNumber(cart.getNumber() - 1);
        if (cart.getNumber() <= 0) {
            removeById(cart);
        } else {
            updateById(cart);
        }
        return cart;
    }
}