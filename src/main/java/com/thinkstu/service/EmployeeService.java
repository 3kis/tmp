package com.thinkstu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thinkstu.common.R;
import com.thinkstu.entity.Employee;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

/**
 * @author : Asher
 * @since : 2024-04/12, 9:28 AM, 周四
 **/

public interface EmployeeService extends IService<Employee> {
    R<Employee> login(HttpSession session, @RequestBody Employee employee);

    void check(Employee employee);
}
