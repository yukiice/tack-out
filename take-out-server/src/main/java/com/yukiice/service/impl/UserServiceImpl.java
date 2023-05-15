package com.yukiice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yukiice.common.CustomException;
import com.yukiice.dto.CodeDto;
import com.yukiice.entity.User;
import com.yukiice.mapper.UserMapper;
import com.yukiice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/14 16:03
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserService userService;

    @Autowired
    StringRedisTemplate template;

    @Override
    public boolean checkUser(HttpServletRequest request,String name) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(User::getPhone,name);
       User user =  userService.getOne(queryWrapper);
       if (user == null){
           throw  new CustomException("该用户不存在！");
       }
        return true;
    }

    @Override
    public boolean checkCode(HttpServletRequest request, CodeDto codeDto) {
        //       redis中获取验证码
        Object codeIn =  template.opsForValue().get(codeDto.getPhone());
        if (codeIn == null){
            throw  new CustomException("验证码失效！");
        }
        if (!Objects.equals(codeDto.getCode(), codeIn)) {
            throw  new CustomException("验证码不正确！");
        }
        return  true;
    }
}
