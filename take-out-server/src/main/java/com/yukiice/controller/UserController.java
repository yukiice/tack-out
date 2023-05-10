package com.yukiice.controller;

import com.yukiice.common.R;
import com.yukiice.dto.CodeDto;
import com.yukiice.entity.User;
import com.yukiice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

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

    @Resource
    JavaMailSender sender;

    @Value("${spring.mail.username}")
    String from;
    @PostMapping("/sendMsg")
    public R<String> sendMsg(HttpServletRequest request, @RequestBody User user){
        if (user.getPhone().isEmpty()) return R.error("请填入相应的邮箱号");
        if (!userService.checkUser(request,user.getPhone())) return R.error("用户不存在");
        SimpleMailMessage message = new SimpleMailMessage();
//        redis处理
        Random random  = new Random();
        int code =  random.nextInt(8999) + 1000;
        //设置邮件标题
        message.setSubject("验证码为:"+code);
        //设置邮件内容
        message.setText("can can need");
        //设置邮件发送给谁，可以多个，这里就发给你的QQ邮箱
        message.setTo(user.getPhone());
        //邮件发送者，这里要与配置文件中的保持一致
        message.setFrom("javatestyukiice@163.com");
        //OK，万事俱备只欠发送
        sender.send(message);
        log.info("用户为:{}",user);
        request.getSession().setAttribute("emailCode",String.valueOf(code));
        return  R.success("验证码发送成功");
    }
    @PostMapping(value = "/login")
    public R<String> login(HttpServletRequest request,@RequestBody CodeDto codeDto){
        userService.checkCode(request,codeDto);
        return  R.success("登录成功！");
    }
}
