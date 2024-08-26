package com.cw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cw.entity.Result;
import com.cw.entity.Storage;
import com.cw.service.StorageService;
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
 * @since 2024-08-19
 */
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Resource
    private StorageService storageService;

    @PostMapping
    public Result add(@RequestBody @Valid Storage storage){
        boolean save = storageService.save(storage);
        return save?Result.success():Result.error("该仓库已存在");
    }
    @DeleteMapping()
    public Result del( Integer id){
        storageService.removeById(id);
        return Result.success();
    }
    @GetMapping
    public Result list(@RequestParam(defaultValue = "1",required = false)Integer pageNum,@RequestParam(defaultValue = "5",required = false)Integer pageSize){
        QueryWrapper<Storage> query = Wrappers.query();
        Page<Storage> page = storageService.page(new Page<>(pageNum, pageSize),query);

        return Result.success(page);
    }
    @PutMapping
    public Result update(@RequestBody @Valid Storage storage){
        if(storage.getId()==null){
            return Result.error("仓库id错误");
        }
        storageService.updateById(storage);
        return Result.success();
    }
    @GetMapping("/search")
    public Result search(@RequestParam String searchContext,@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        QueryWrapper<Storage> query = Wrappers.query();
        query.like("name",searchContext);
        Page<Storage> page = storageService.page(new Page<>(pageNum,pageSize), query);
        return Result.success(page);
    }
    @GetMapping("/listAll")
    public Result listAll(){
        List<Storage> list = storageService.list();
        return Result.success(list);
    }
}
