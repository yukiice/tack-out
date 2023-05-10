package com.yukiice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yukiice.dto.CodeDto;
import com.yukiice.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {
    public boolean checkUser(HttpServletRequest request,String name);

    public boolean checkCode(HttpServletRequest request, CodeDto codeDto);
}
