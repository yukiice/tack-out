package com.yukiice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yukiice.common.BaseContext;
import com.yukiice.common.R;
import com.yukiice.entity.ShoppingCart;
import com.yukiice.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/27 16:02
 */
@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    /**
     * 添加到购物车里面
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        ShoppingCart cart =  shoppingCartService.addGoods(shoppingCart);
        return  R.success(cart);
    }
    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart){
        shoppingCartService.subGoods(shoppingCart);
        return R.success("删除成功");
    }
    @GetMapping("/list")
    public R<List<ShoppingCart>> getList(){
        List<ShoppingCart> list = shoppingCartService.getCartList();
        return  R.success(list);
    }
    @DeleteMapping("/clean")
    public R<String> delete(){
        shoppingCartService.clearCart();
        return  R.success("清空成功");
    }
}
