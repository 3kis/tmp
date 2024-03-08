package com.thinkstu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.thinkstu.entity.Category;

/**
 * 菜品及套餐分类(Category)表服务接口
 *
 * @author asher
 * @since 2023-04-14 12:06:36
 */
public interface CategoryService extends IService<Category> {
    void check(Long id);
}