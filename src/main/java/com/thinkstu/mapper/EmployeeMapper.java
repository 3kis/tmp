package com.thinkstu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinkstu.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : Asher
 * @since : 2024-04/12, 9:26 AM, 周四
 **/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
