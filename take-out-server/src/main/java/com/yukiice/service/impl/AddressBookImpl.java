package com.yukiice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yukiice.common.BaseContext;
import com.yukiice.common.CustomException;
import com.yukiice.entity.AddressBook;
import com.yukiice.mapper.AddressBookMapper;
import com.yukiice.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/28 14:34
 */
@Service
@Slf4j
public class AddressBookImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    @Override
    public AddressBook backDetail(Long id) {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getId,id);
        AddressBook detail = this.getOne(queryWrapper);
        if (detail == null){
            throw new CustomException("该地址不存在");
        }
        return detail;
    }

    @Override
    public void AddBook(AddressBook book) {
        boolean status = this.save(book);
        if (!status){
            throw new CustomException("保存失败!");
        }
    }

    @Override
    public void changeDefault(AddressBook book) {
        LambdaUpdateWrapper<AddressBook> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(AddressBook::getId,BaseContext.getCurrentId());
        queryWrapper.set(AddressBook::getIsDefault,0);
        this.update(queryWrapper);
        book.setIsDefault(1);
        this.updateById(book);
    }

    @Override
    public AddressBook getDefaultOne() {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getIsDefault,1);
        return this.getOne(queryWrapper);
    }
}
