package com.thinkstu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thinkstu.common.R;
import com.thinkstu.entity.Employee;
import com.thinkstu.service.impl.EmployeeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author : Asher
 * @since : 2023-04/12, 9:53 AM, 周四
 **/
@Api(tags = "后台用户系统")
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl service;


    @ApiOperation("登录账号")
    @PostMapping("/login")
    public R<Employee> login(HttpSession session, @RequestBody Employee employee) {
        return service.login(session, employee);
    }

    @ApiOperation("退出账号")
    @PostMapping("/logout")
    public R<String> logout() {
        return R.success("退出成功~");
    }

    @ApiOperation("注册账号")
    @PostMapping
    R<String> save(@RequestBody Employee employee) {
        // 默认密码 123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        service.save(employee);
        return R.success("添加用户成功~");
    }

    @ApiOperation("分页显示")
    @GetMapping("page")
    R<Page> pages(Integer page, Integer pageSize, String name) {
        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNoneEmpty(name), Employee::getName, name);
        service.page(pageInfo, wrapper);
        return R.success(pageInfo);
    }


    @ApiOperation("更新信息")
    @PutMapping
    R<String> update(@RequestBody Employee employee) {
        service.check(employee);  // check权限
        service.updateById(employee);
        return R.success("用户信息更改成功~");
    }

    @ApiOperation("获取账号数据")
    @GetMapping("{id}")
    R<Employee> getOne(@PathVariable("id") Long id) {
        Employee one = service.getById(id);
        return one != null ? R.success(one) : R.error("信息获取失败！");
    }
}





