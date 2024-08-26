package com.cw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cw.entity.Goods;
import com.cw.mapper.GoodsMapper;
import com.cw.service.GoodsService;
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
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {


    @Resource
    private GoodsMapper goodsMapper;
    @Override
    public Goods queryGoodsById(Integer id) {
        return goodsMapper.queryGoodsById(id);
    }
}
