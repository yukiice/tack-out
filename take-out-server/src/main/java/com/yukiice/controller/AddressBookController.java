package com.yukiice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yukiice.common.R;
import com.yukiice.entity.AddressBook;
import com.yukiice.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/27 16:07
 */
@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {
    @Autowired
    AddressBookService addressBookService;

    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> list = addressBookService.list(queryWrapper);
        return R.success(list);
    }

    /**
     * 获取详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> getDetail(@PathVariable Long id){
        AddressBook book = addressBookService.backDetail(id);
        return R.success(book);
    }

    @PostMapping
    public R<String> addBook(@RequestBody AddressBook book){
        addressBookService.AddBook(book);
        return R.success("成功");
    }

    @PutMapping("/{id}")
    public R<String> changeDefault(@RequestBody AddressBook addressBook){
        addressBookService.changeDefault(addressBook);
       return R.success("更改默认地址成功");
    }

    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        return  R.success(addressBookService.getDefaultOne());
    }
}
