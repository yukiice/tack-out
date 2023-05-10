package com.yukiice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yukiice.common.BaseContext;
import com.yukiice.entity.ShoppingCart;
import com.yukiice.mapper.ShoppingCartMapper;
import com.yukiice.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/5/5 10:52
 */
@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Autowired
    ShoppingCartService shoppingCartService;
    @Override
    public ShoppingCart addGoods(ShoppingCart shoppingCart) {
//       设置用户ID
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
//        查询当前菜品或者套餐是否在购物车中
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        Long dishId = shoppingCart.getDishId();
        if (dishId != null){
            //菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else{
            //套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart cart = shoppingCartService.getOne(queryWrapper);
        if (cart != null){
            Integer number = cart.getNumber();
            cart.setNumber(number+1);
            shoppingCartService.updateById(cart);
        }else{
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            cart = shoppingCart;
        }
        return cart;
    }

    @Override
    public List<ShoppingCart> getCartList() {
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        return shoppingCartService.list(queryWrapper);
    }

    @Override
    public void subGoods(ShoppingCart shoppingCart) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        if (shoppingCart.getDishId() != null){
            queryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else{
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart cart = shoppingCartService.getOne(queryWrapper);
        if (cart.getNumber() == 1){
            shoppingCartService.removeById(cart);
        }else{
            cart.setNumber(cart.getNumber() - 1);
            shoppingCartService.updateById(cart);
        }
    }

    @Override
    public void clearCart() {
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        shoppingCartService.remove(queryWrapper);
    }
}
