package com.thinkstu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.thinkstu.entity.AddressBook;

/**
 * 地址管理(AddressBook)表服务接口
 *
 * @author asher
 * @since 2024-04-26 04:47:40
 */
public interface AddressBookService extends IService<AddressBook> {
    AddressBook setDafault(AddressBook addressBook);

    AddressBook getDefault();
}