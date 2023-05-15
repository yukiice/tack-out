package com.yukiice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yukiice.common.R;
import com.yukiice.entity.Employee;
import com.yukiice.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    StringRedisTemplate template;

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
        log.info("拿到的id为:{}",emp.getId());
        template.opsForValue().set(String.valueOf(emp.getId()),String.valueOf(emp.getId()));
        request.getSession().setAttribute("user",emp.getId());
        return  R.success(emp);
    }

//    退出
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return R.success("退出成功!");
    }

//    新增员工
    @PostMapping
    public R<String> save(@RequestBody Employee employee){
        log.info("员工信息"+employee.toString());
//        初始化加密MD5密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        System.out.println(employee);
        employeeService.save(employee);
        return  R.success("新增员工成功");
    }


    /**
     * 根据分页和条件获取列表数据
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> getList(int page, int pageSize, String name){
        log.info("page:{},pageSize:{},name:{}",page,pageSize,name);
        Page<Employee> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
//        条件查询
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
//        排序
        queryWrapper.orderByDesc(Employee::getUpdateTime);
//        执行查询
        employeeService.page(pageInfo,queryWrapper);
        return  R.success(pageInfo);
    }

    @PutMapping
    public R<String> changeStatus(HttpServletRequest request,@RequestBody Employee employee){
        boolean res =  employeeService.updateById(employee);
        if (!res) return  R.error("更新失败。。。");
        return  R.success("员工修改状态成功！");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息");
        Employee employee =  employeeService.getById(id);
        return  R.success(employee);
    }

}
