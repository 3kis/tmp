package com.thinkstu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinkstu.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息(User)表数据库访问层
 *
 * @author asher
 * @since 2024-04-16 10:51:45
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}