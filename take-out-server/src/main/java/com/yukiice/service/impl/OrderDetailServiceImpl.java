package com.yukiice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yukiice.entity.OrderDetail;
import com.yukiice.mapper.OrderDetailMapper;
import com.yukiice.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/5/5 15:44
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
