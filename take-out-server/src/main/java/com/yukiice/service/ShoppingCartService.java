package com.yukiice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yukiice.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {
    public ShoppingCart addGoods(ShoppingCart shoppingCart);

    public List<ShoppingCart> getCartList();

    public void subGoods(ShoppingCart shoppingCart);

    public void clearCart();
}
