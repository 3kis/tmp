package com.thinkstu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinkstu.common.MyCheckException;
import com.thinkstu.entity.User;
import com.thinkstu.mapper.UserMapper;
import com.thinkstu.service.UserService;
import com.thinkstu.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户信息(User)表服务实现类
 *
 * @author asher
 * @since 2024-04-16 10:51:45
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    StringRedisTemplate redis;

    @Override
    public void sendMsg(HttpSession session, User user) {
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            // 默认重新赋值，模拟短信验证码过程
            code = 1234;
            log.info("code = {}", code);
/*            SMSUtils.sendMessage(phone, code);
            key = 手机号码   ，value = 验证码
            session.setAttribute(phone, code);*/
            // 验证码有效期：5分钟。
            redis.opsForValue().set(phone, String.valueOf(code), 5L, TimeUnit.MINUTES);
        } else {
            throw new MyCheckException("短信验证码发送失败！");
        }
    }

    @Override
    public User login(HttpSession session, Map map) {
        session.setAttribute("employee", null);
        String phone = (String) map.get("phone");
        Object check_code = map.get("code");
        // Object code       = session.getAttribute(phone);
        Object code = redis.opsForValue().get(phone);

        // 验证码匹配：查询数据库中是否存在该用户，不存在则直接注册
        if (code != null && code.toString().equals(check_code.toString())) {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getPhone, phone);
            User user = getOne(wrapper);
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                save(user);
            }
            session.setAttribute("user", user.getId());
            redis.delete(phone);
            return user;
        } else {
            throw new MyCheckException("登录失败！");
        }
    }
}