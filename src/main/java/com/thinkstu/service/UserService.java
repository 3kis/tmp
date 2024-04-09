package com.thinkstu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.thinkstu.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 用户信息(User)表服务接口
 *
 * @author asher
 * @since 2024-04-16 10:51:45
 */
public interface UserService extends IService<User> {
    void sendMsg(HttpSession session, User user);

    User login(HttpSession session, Map map);
}