package com.cw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cw.entity.GoodsType;
import com.cw.mapper.GoodsTypeMapper;
import com.cw.service.GoodsTypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2024-08-23
 */
@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {
    @Resource
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    public GoodsType queryGoodsTypeById(Integer id) {
        return goodsTypeMapper.queryGoodsTypeById(id);
    }

    @Override
    public GoodsType queryGoodsTypeByGoodsId(Integer id) {
        return goodsTypeMapper.queryGoodsTypeByGoodsId(id);
    }
}
