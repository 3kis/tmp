package com.thinkstu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinkstu.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * 地址管理(AddressBook)表数据库访问层
 *
 * @author asher
 * @since 2024-04-26 04:47:40
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}