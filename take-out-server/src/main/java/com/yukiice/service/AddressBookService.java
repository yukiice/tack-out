package com.yukiice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yukiice.entity.AddressBook;

public interface AddressBookService extends IService<AddressBook> {
   public AddressBook backDetail(Long id);

   public void AddBook(AddressBook book);

   public void  changeDefault(AddressBook book);

   public AddressBook getDefaultOne();
}
