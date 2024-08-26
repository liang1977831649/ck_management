package com.cw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cw.entity.Storage;

public interface StorageService extends IService<Storage>{
    Storage queryStorageById(Integer id);

    Storage queryStorageByGoodsId(Integer id);
}
