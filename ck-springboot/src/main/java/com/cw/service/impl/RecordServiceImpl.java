package com.cw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cw.entity.Record;
import com.cw.mapper.RecordMapper;
import com.cw.service.RecordService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2024-08-24
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Resource
    private RecordMapper recordMapper;

    //@Override
    //public IPage<Record> listServer(Integer pageNum,Integer pageSize,String name, Integer storageId, Integer goodstypeId) {
    //    IPage<Record> page = recordMapper.list(new Page<>(pageNum, pageSize), name, storageId, goodstypeId);
    //    return  page;
    //}

    @Override
    public IPage<Record> listServer2(Integer pageNum, Integer pageSize, String name, Integer storageId, Integer goodstypeId) {
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("goods_id","select goods.id from goods where name like '%"+name+"%'");
        queryWrapper.inSql("goods_id","select goods.id from goods where storage_id="+storageId);
        queryWrapper.inSql("goods_id","select goods.id from goods where goodstype_id="+goodstypeId);
        Page<Record> page = this.page(new Page<>(pageNum, pageSize), queryWrapper);
        return page;
    }

}
