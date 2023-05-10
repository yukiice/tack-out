package com.yukiice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yukiice.entity.Orders;

public interface OrdersService extends IService<Orders> {
    public void submitOrder(Orders orders);
}
