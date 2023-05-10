package com.yukiice.controller;

import com.yukiice.common.R;
import com.yukiice.dto.OrdersDto;
import com.yukiice.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/5/5 14:51
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrdersController {
    @PostMapping("/submit")
    public R<String> sendOrder(@RequestBody Orders orders){
        System.out.println(orders);
        return  null;
    }
}
