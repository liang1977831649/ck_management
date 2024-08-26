package com.cw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cw.entity.GoodsType;
import com.cw.entity.Result;
import com.cw.service.GoodsTypeService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2024-08-23
 */
@RestController
@RequestMapping("/goodsType")
public class GoodsTypeController {

    @Resource
    private GoodsTypeService goodsTypeService;

    @PostMapping
    public Result add(@RequestBody @Valid GoodsType goodsType){
        boolean save = goodsTypeService.save(goodsType);
        return save?Result.success():Result.error("该食品类已存在");
    }
    @DeleteMapping()
    public Result del( Integer id){
        goodsTypeService.removeById(id);
        return Result.success();
    }
    @GetMapping
    public Result list(@RequestParam(defaultValue = "1",required = false)Integer pageNum,@RequestParam(defaultValue = "5",required = false)Integer pageSize){
        QueryWrapper<GoodsType> query = Wrappers.query();
        Page<GoodsType> page = goodsTypeService.page(new Page<>(pageNum, pageSize),query);
        return Result.success(page);
    }
    @PutMapping
    public Result update(@RequestBody @Valid GoodsType goodsType){
        if(goodsType.getId()==null){
            return Result.error("食品id错误");
        }
        goodsTypeService.updateById(goodsType);
        return Result.success();
    }
    @GetMapping("/search")
    public Result search(@RequestParam String searchContext,@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        QueryWrapper<GoodsType> query = Wrappers.query();
        query.like("name",searchContext);
        Page<GoodsType> page = goodsTypeService.page(new Page<>(pageNum,pageSize), query);
        return Result.success(page);
    }
    @GetMapping("listAll")
    public Result listAll(){
        List<GoodsType> list = goodsTypeService.list();
        return Result.success(list);
    }
}
