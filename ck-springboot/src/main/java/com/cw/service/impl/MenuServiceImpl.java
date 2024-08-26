package com.cw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cw.entity.Menu;
import com.cw.mapper.MenuMapper;
import com.cw.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
}
