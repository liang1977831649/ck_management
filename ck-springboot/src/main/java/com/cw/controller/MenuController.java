package com.cw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cw.entity.Menu;
import com.cw.entity.Result;
import com.cw.service.MenuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    @GetMapping("/getMenu/{roleId}")
    public Result getMenu(@PathVariable String roleId){
        QueryWrapper<Menu> query = Wrappers.query();
        query.like("menu_right",roleId);
        List<Menu> list = menuService.list(query);
        return Result.success(list);
    }
}
