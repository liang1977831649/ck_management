package com.cw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cw.entity.Storage;
import com.cw.mapper.StorageMapper;
import com.cw.service.StorageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {

    @Resource
    private StorageMapper storageMapper;
    @Override
    public Storage queryStorageById(Integer id) {
        return storageMapper.queryStorageById(id);
    }

    @Override
    public Storage queryStorageByGoodsId(Integer id) {
        return storageMapper.queryStorageByGoodsId(id);
    }


}
