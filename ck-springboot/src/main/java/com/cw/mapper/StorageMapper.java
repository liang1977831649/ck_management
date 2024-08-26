package com.cw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cw.entity.Storage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StorageMapper extends BaseMapper<Storage> {
    Storage queryStorageById(Integer id);

    Storage queryStorageByGoodsId(Integer id);
}
