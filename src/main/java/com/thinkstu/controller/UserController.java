package com.thinkstu.controller;


import com.thinkstu.common.R;
import com.thinkstu.entity.User;
import com.thinkstu.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 用户信息(User)表控制层
 *
 * @author asher
 * @since 2024-04-16 10:51:45
 */
@Slf4j
@Api(tags = "前台用户系统")
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserServiceImpl service;

    @PostMapping("sendMsg")
    @ApiOperation("短信验证码接口")
    R<String> sendMsg(HttpSession session, @RequestBody User user) {
        service.sendMsg(session, user);
        return R.success("短信验证码发送成功~");
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    R<User> login(HttpSession session, @RequestBody Map map) {
        return R.success(service.login(session, map));
    }

    @PostMapping("loginout")
    @ApiOperation("用户退出")
    R<String> logout() {
        return R.success("账号退出成功~");
    }

}