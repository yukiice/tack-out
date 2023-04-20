package com.yukiice.controller;

import com.yukiice.common.R;
import com.yukiice.entity.User;
import com.yukiice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/14 16:04
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user){
        log.info("用户为:{}",user);
        return  null;
    }
}
