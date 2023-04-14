package com.yukiice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yukiice.entity.User;
import com.yukiice.mapper.UserMapper;
import com.yukiice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/14 16:03
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
