package com.thinkstu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thinkstu.common.R;
import com.thinkstu.entity.Category;
import com.thinkstu.service.impl.CategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品及套餐分类(Category)表控制层
 *
 * @author asher
 * @since 2024-04-14 12:47:01
 */
@Api(tags = "菜品分类系统")
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryServiceImpl service;

    @ApiOperation("新增分类")
    @PostMapping
    R<String> add(@RequestBody Category category) {
        service.save(category);
        return R.success("新建分类成功~");
    }

    @ApiOperation("分页显示")
    @GetMapping("/page")
    R<Page> pages(Long page, Long pageSize) {
        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        service.page(pageInfo, wrapper);
        return R.success(pageInfo);
    }

    @ApiOperation("删除分类")
    @DeleteMapping
    R<String> delete(Long id) {
        // 当存在相关联数据时，分类无法删除
        service.check(id);
        service.removeById(id);
        return R.success("删除成功~");
    }

    @ApiOperation("更新分类")
    @PutMapping
    R<String> update(@RequestBody Category category) {
        service.updateById(category);
        return R.success("修改成功~");
    }

    @ApiOperation("显示分类列表信息")
    @GetMapping("list")
    R<List<Category>> lists(Category category) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(category.getType() != null, Category::getType, category.getType())
                .orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        return R.success(service.list(wrapper));
    }
}






