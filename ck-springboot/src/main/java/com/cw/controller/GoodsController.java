package com.cw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cw.entity.Goods;
import com.cw.entity.GoodsType;
import com.cw.entity.Result;
import com.cw.entity.Storage;
import com.cw.service.GoodsService;
import com.cw.service.GoodsTypeService;
import com.cw.service.StorageService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2024-08-23
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private GoodsService goodsService;
    @Resource
    private StorageService storageService;
    @Resource
    private GoodsTypeService goodsTypeService;

    @PostMapping
    public Result add(@RequestBody Goods goods){

        if(goods.getStorageId()!=null){
            QueryWrapper<Storage> query = Wrappers.query();
            query.eq("id",goods.getStorageId());
            long count = storageService.count(query);
            if(count==0){
                return Result.error("该仓库不存在");
            }
        }
        if(goods.getGoodstypeId()!=null){
            QueryWrapper<GoodsType> query = Wrappers.query();
            query.eq("id",goods.getGoodstypeId());
            long count = goodsTypeService.count(query);
            if(count==0){
                return Result.error("该物品分类不存在");
            }
        }

        boolean save = goodsService.save(goods);
        return save?Result.success():Result.error("主键已存在");
    }
    @DeleteMapping
    public Result del(Integer id){
        goodsService.removeById(id);
        return Result.success();
    }
    @PutMapping
    public Result update(@RequestBody Goods goods){
        goodsService.updateById(goods);
        return Result.success();
    }
    @GetMapping("/list")
    public Result list(@RequestParam Map<String, Object> maps){

        String pageNum = (String) maps.get("pageNum");
        String pageSize =(String)maps.get("pageSize");
        String name = (String)maps.get("name");
        String storageId =String.valueOf( maps.get("storageId"));
        String goodstypeId = String.valueOf(maps.get("goodstypeId"));

        QueryWrapper<Goods> query = Wrappers.query();

        if(ObjectUtils.isNull(pageNum)){
            pageNum="1";
        }if(ObjectUtils.isNull(pageSize)){
            pageSize="5";
        }

        if(StringUtils.isNotEmpty(name)){
            query.like("name",name);
        }
        //如果传过来的数字是一个到多个
        if(Pattern.matches("\\d+",storageId)){
            query.eq("storage_id",storageId);
        }
        if(Pattern.matches("\\d+",goodstypeId)){
            query.eq("goodstype_id",goodstypeId);
        }

        Page<Goods> page = goodsService.page(new Page<>(Integer.parseInt(pageNum),Integer.parseInt( pageSize)), query);
        return Result.success(page);
    }
}
