package com.cw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cw.entity.Goods;
import com.cw.entity.Record;
import com.cw.entity.Result;
import com.cw.service.*;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2024-08-24
 */
@RestController
@RequestMapping("/record")
public class RecordController {
    @Resource
    private RecordService recordService;

    @Resource
    private UserService userService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private GoodsTypeService goodsTypeService;

    @Resource
    private StorageService storageService;


    @GetMapping("/list")
    public Result list(@RequestParam Map<String ,Object> map){
        String name =(String) map.get("searchContext");
        String pageSize =(String)  map.get("pageSize");
        String pageNum = (String) map.get("pageNum");
        String storageId = (String) map.get("storageId");
        String goodstypeId = (String) map.get("goodstypeId");

        if(StringUtils.isEmpty(pageSize)){
            pageSize="5";
        }if(StringUtils.isEmpty(pageNum)){
            pageNum="1";
        }

        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();

        //如果姓名不为空，那就加上条件->子查询
        if(!StringUtils.isEmpty(name)){
            queryWrapper.inSql("goods_id","select goods.id from goods where name like '%"+name+"%'");
        }
        //如果storageId不为空，那就加上条件->子查询
        if(!StringUtils.isEmpty(storageId)){
            queryWrapper.inSql("goods_id","select goods.id from goods where storage_id="+Integer.parseInt(storageId));
        }
        //如果goodstypeId不为空，那就加上条件->子查询
        if(!StringUtils.isEmpty(goodstypeId)){
            queryWrapper.inSql("goods_id","select goods.id from goods where goodstype_id="+Integer.parseInt(goodstypeId));
        }

        //查询结果
        Page<Record> page = recordService.page(new Page<>(Integer.parseInt(pageNum),Integer.parseInt( pageSize)),
                queryWrapper);

        //给字段具有的名称name复制
        List<Record> records = page.getRecords();
        for (Record record : records) {
            try {
                record.setUserName((userService.queryUserById(record.getUserId()).getName()));

                record.setAdminName(((userService.queryUserById(record.getAdminId())).getName()));

                record.setGoodsName((goodsService.queryGoodsById(record.getGoodsId())).getName());

                record.setStorageName((storageService.queryStorageByGoodsId(record.getGoodsId()).getName()));

                record.setGoodstypeName((goodsTypeService.queryGoodsTypeByGoodsId(record.getGoodsId()).getName()));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("系统错误");

            }
        }

        return Result.success(page);
    }


    @PostMapping
    public Result save(@RequestBody @Valid Record record){
        //System.out.printf("record="+record);
        Goods goods = goodsService.queryGoodsById(record.getGoodsId());
        // 如果是出库，那么商品的数量要大于等于出库的数量
        if("2".equals(record.getOperation())&&goods.getCount()+record.getCount()<=0){
            return Result.error("商品数量不足，不能出库");
        }
        int newCount = goods.getCount() + record.getCount();
        goods.setCount(newCount);
        //更新
        goodsService.updateById(goods);
        //设置更新时间
        record.setCreatetime(LocalDateTime.now());
        recordService.save(record);
        return Result.success();
    }
}
