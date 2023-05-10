package com.yukiice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yukiice.common.BaseContext;
import com.yukiice.common.CustomException;
import com.yukiice.entity.AddressBook;
import com.yukiice.entity.Orders;
import com.yukiice.entity.ShoppingCart;
import com.yukiice.mapper.OrdersMapper;
import com.yukiice.service.AddressBookService;
import com.yukiice.service.OrdersService;
import com.yukiice.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/5/5 14:54
 */
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    ShoppingCartService cartService;

    @Autowired
    AddressBookService addressBookService;
    @Override
    public void submitOrder(Orders orders){
//        查询购物车数据
        Long id = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapperOne = new LambdaQueryWrapper<>();
        queryWrapperOne.eq(ShoppingCart::getUserId,id);
        List<ShoppingCart> shoppingCarts = cartService.list(queryWrapperOne);
        if (shoppingCarts == null || shoppingCarts.size() == 0){
            throw new CustomException("购物车不能为空");
        }
//        查询地址数据
        AddressBook addressBook =  addressBookService.getById(orders.getAddressBookId());
//   处理数据
        Long orderId = IdWorker.getId();
        orders.setNumber(String.valueOf(orderId));

    }
}
