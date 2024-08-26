package com.cw.service;

import com.cw.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2024-08-23
 */
public interface GoodsService extends IService<Goods> {
    Goods queryGoodsById(Integer id);
}
