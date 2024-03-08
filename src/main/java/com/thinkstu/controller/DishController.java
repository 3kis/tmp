package com.thinkstu.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thinkstu.common.R;
import com.thinkstu.dto.DishDTO;
import com.thinkstu.entity.Dish;
import com.thinkstu.service.impl.DishServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理(Dish)表控制层
 *
 * @author asher
 * @since 2023-04-14 16:05:36
 */
@Api(tags = "菜品系统")
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    DishServiceImpl service;

    @PostMapping
    @ApiOperation("新增菜品")
    @CacheEvict(value = "dishCache", allEntries = true)
    public R<String> add(@RequestBody DishDTO dishDto) {
        service.add(dishDto);
        return R.success("新增菜品成功~");
    }

    // redis缓存，存储分页信息
    @GetMapping("page")
    @ApiOperation("分页显示")
    @Cacheable(value = "dishCache", key = "#page+'_'+#pageSize+'_'+#name")
    public R<Page<DishDTO>> pages(Long page, Long pageSize, String name) {
        log.info("分页结果===>{}", service.myPage(page, pageSize, name));
        return R.success(service.myPage(page, pageSize, name));
    }

    @DeleteMapping
    @ApiOperation("删除菜品")
    @CacheEvict(value = "dishCache", allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids) {
        service.delete(ids);
        return R.success("删除成功~");
    }

    @PostMapping("/status/{status}")
    @ApiOperation("更新销售状态")
    @CacheEvict(value = "dishCache", allEntries = true)
    public R<String> stopSell(@PathVariable("status") Integer status, Long... ids) {
        LambdaUpdateWrapper<Dish> wrapper = wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(ids != null, Dish::getId, ids).set(Dish::getStatus, status);
        service.update(wrapper);
        return R.success("修改成功~");
    }

    @GetMapping("list")
    @ApiOperation("获取菜品列表信息")
    @Cacheable(value = "dishCache", key = "#dish.categoryId+'_'+#dish.status")
    public R<List<DishDTO>> getBatchDish(Dish dish) {
        return R.success(service.getBatchDish(dish.getCategoryId()));
    }

    @GetMapping("{id}")
    @ApiOperation("获取单份菜品")
    R<DishDTO> getSingle(@PathVariable("id") Long id) {
        return R.success(service.getSingle(id));
    }

    @PutMapping
    @ApiOperation("更新菜品")
    @CacheEvict(value = "dishCache", allEntries = true)
    public R<String> update(@RequestBody DishDTO dto) {
        service.myUpdate(dto);
        return R.success("更新成功~");
    }


}







