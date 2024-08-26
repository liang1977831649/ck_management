package com.cw.service;

import com.cw.entity.GoodsType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2024-08-23
 */
public interface GoodsTypeService extends IService<GoodsType> {
    GoodsType queryGoodsTypeById(Integer id);

    GoodsType queryGoodsTypeByGoodsId(Integer id);
}
