package com.thinkstu.controller;


import com.baomidou.mybatisplus.core.conditions.query.*;
import com.thinkstu.common.*;
import com.thinkstu.entity.*;
import com.thinkstu.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(tags = "地址管理系统")
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService service;

    @PostMapping
    @ApiOperation("新增地址")
    public R<String> save(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        // 新增地址将作为默认地址
        service.save(addressBook);
        service.setDafault(addressBook);
        return R.success("添加地址成功~");
    }

    @PutMapping
    @ApiOperation("更新地址")
    public R<String> update(@RequestBody AddressBook addressBook) {
        service.updateById(addressBook);
        return R.success("地址更新成功~");
    }

    @PutMapping("default")
    @ApiOperation("设置默认地址")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook) {
        return R.success(service.setDafault(addressBook));
    }


    @GetMapping("/{id}")
    @ApiOperation("查询用户地址")
    public R get(@PathVariable Long id) {
        AddressBook addressBook = service.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        } else {
            throw new MyCheckException("没有找到该对象!");
        }
    }

    @GetMapping("default")
    @ApiOperation("查询用户默认地址")
    public R<AddressBook> getDefault() {return R.success(service.getDefault());}

    @GetMapping("/list")
    @ApiOperation("查询用户所有地址")
    public R<List<AddressBook>> list(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        Long                            userId  = addressBook.getUserId();
        wrapper.eq(null != userId, AddressBook::getUserId, userId)
                .orderByDesc(AddressBook::getUpdateTime);
        return R.success(service.list(wrapper));
    }
}
