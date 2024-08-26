package com.cw.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cw.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2024-08-24
 */
public interface RecordService extends IService<Record> {
    //IPage<Record> listServer(Integer pageNum,Integer pageSize,String name,Integer storageId,Integer goodstypeId);
    IPage<Record> listServer2(Integer pageNum,Integer pageSize,String name,Integer storageId,Integer goodstypeId);
}
