package com.thinkstu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinkstu.common.BaseContext;
import com.thinkstu.common.MyCheckException;
import com.thinkstu.entity.AddressBook;
import com.thinkstu.mapper.AddressBookMapper;
import com.thinkstu.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * 地址管理(AddressBook)表服务实现类
 *
 * @author asher
 * @since 2024-04-26 04:47:40
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    @Override
    public AddressBook setDafault(AddressBook addressBook) {
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId()).set(AddressBook::getIsDefault, 0);
        // 先把所有地址默认等级都设为 0
        update(wrapper);
        // update address_book set is_default = 1 where id = ?
        addressBook.setIsDefault(1);
        updateById(addressBook);
        return addressBook;
    }

    @Override
    public AddressBook getDefault() {
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId())
                .eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = getOne(wrapper);
        if (addressBook == null) {
            throw new MyCheckException("没有找到该对象!");
        }
        return addressBook;
    }
}