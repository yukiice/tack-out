package com.yukiice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yukiice.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
