package com.yukiice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yukiice.common.R;
import com.yukiice.entity.Employee;
import com.yukiice.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     *员工登录模块
     * @param request
     * @param employee
     * @return
     */
    @PostMapping(value = "/login")
    public R<Employee> login(HttpServletRequest request,@RequestBody Employee employee){
//        1.md5加密密码
        String password = employee.getPassword();
        password =  DigestUtils.md5DigestAsHex(password.getBytes());
//        2.根据用户名来查询用户
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
//        3.看下数据是否查询到
        if (emp == null) return  R.error("登录失败");  // 登录失败返回
//        4.密码比对
        if (!emp.getPassword().equals(password)) return R.error("密码错误");  //密码错误返回
//        5.查询员工状态
        if (emp.getStatus() == 0) return R.error("该员工账号已被冻结");
//        6.登录成功，返回员工信息
        request.getSession().setAttribute("employee",employee.getId());
        return  R.success(emp);
    }
}
