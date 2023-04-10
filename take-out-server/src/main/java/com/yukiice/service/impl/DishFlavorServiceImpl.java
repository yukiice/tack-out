package com.yukiice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yukiice.dto.DishDto;
import com.yukiice.entity.DishFlavor;
import com.yukiice.mapper.DishFlavorMapper;
import com.yukiice.service.DishFlavorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yukiice
 * @version 1.0
 * Create by 2023/4/10 13:52
 */
@Service
@Slf4j
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
