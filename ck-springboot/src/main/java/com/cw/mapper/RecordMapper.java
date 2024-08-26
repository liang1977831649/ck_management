package com.cw.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cw.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2024-08-24
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    IPage<Record> list(IPage<Record> page,String name,Integer storageId,Integer goodstyeId);
    Record queryOne(Integer id);
}
