package com.thinkstu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinkstu.common.BaseContext;
import com.thinkstu.common.MyCheckException;
import com.thinkstu.common.R;
import com.thinkstu.entity.Employee;
import com.thinkstu.mapper.EmployeeMapper;
import com.thinkstu.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

/**
 * @author : Asher
 * @since : 2023-04/12, 9:28 AM, 周四
 **/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Override
    public R<Employee> login(HttpSession session, @NotNull Employee employee) {
        // 0. 密码加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        // 1. 用户名比对
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        String username = employee.getUsername();
        queryWrapper.eq(StringUtils.isNoneEmpty(username), Employee::getUsername, username);
        Employee one = getOne(queryWrapper);
        if (one == null) {
            return R.error("用户名不存在！");
        }
        // 2. 密码比对
        if (!password.equals(one.getPassword())) {
            return R.error("密码错误！");
        }
        // 3. 账户是否被冻结
        if (one.getStatus() == 0) {
            return R.error("账户已被冻结！");
        }
        // 4. 放行，存储数据
        session.setAttribute("employee", one.getId());
        return R.success(one);
    }

    @Override
    public void check(Employee employee) {
        if (employee.getId() == 1 && employee.getStatus() == 0) {
            throw new MyCheckException("不能禁用管理员账号！");
        }
        if (employee.getId() == 1 && BaseContext.getCurrentId() != 1) {
            throw new MyCheckException("暂无权限！");
        }
    }
}
