package com.yukiice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yukiice.entity.Employee;
import com.yukiice.mapper.EmployeeMapper;
import com.yukiice.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {

}
