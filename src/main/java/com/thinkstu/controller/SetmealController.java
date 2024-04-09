package com.thinkstu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thinkstu.common.R;
import com.thinkstu.dto.SetmealDTO;
import com.thinkstu.entity.Dish;
import com.thinkstu.entity.Setmeal;
import com.thinkstu.service.impl.SetmealServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 套餐(Setmeal)表控制层
 *
 * @author asher
 * @since 2024-04-14 16:06:16
 */
@Api(tags = "套餐管理系统")
@RestController
@RequestMapping("setmeal")
public class SetmealController {
    @Autowired
    SetmealServiceImpl service;

    @GetMapping("page")
    @ApiOperation("分页显示")
    R<Page<SetmealDTO>> pages(Long page, Long pageSize, String name) {
        return R.success(service.myPage(page, pageSize, name));
    }

    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(value = "setmealCache", key = "#setmealDto.categoryId")
    public R<String> add(@RequestBody SetmealDTO setmealDto) {
        service.add(setmealDto);
        return R.success("添加套餐成功~");
    }

    @GetMapping("/{id}")
    @ApiOperation("获取单条套餐信息")
    R<SetmealDTO> getOne(@PathVariable("id") Long id) {
        return R.success(service.getSingle(id));
    }

    @GetMapping("list")
    @ApiOperation("表获套餐列表信息")
    @Cacheable(value = "setmealCache", key = "#categoryId")
    public R<List<Setmeal>> getBatchSetmeal(Long categoryId) {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Setmeal::getStatus, 1).eq(categoryId != null, Setmeal::getCategoryId, categoryId);
        return R.success(service.list(wrapper));
    }

    @PutMapping
    @ApiOperation("更新套餐")
    @CacheEvict(value = "setmealCache", key = "#setmealDto.categoryId")
    public R<String> update(@RequestBody SetmealDTO setmealDto) {
        service.myUpdate(setmealDto);
        return R.success("更新成功~");
    }

    @DeleteMapping
    @ApiOperation("删除套餐")
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids) {
        service.delete(ids);
        return R.success("删除成功~");
    }

    @PostMapping("/status/{status}")
    @ApiOperation("更新套餐状态")
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R<String> on(@PathVariable("status") Integer status, Long... ids) {
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Setmeal::getId, ids).set(Setmeal::getStatus, status);
        service.update(updateWrapper);
        return R.success("修改成功~");
    }

    @GetMapping("dish/{id}")
    @ApiOperation("获取前台弹窗信息")
    R<List<Dish>> details(@PathVariable Long id) {
        return R.success(service.detailes(id));
    }

}