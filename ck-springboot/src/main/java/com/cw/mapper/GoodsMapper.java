package com.cw.mapper;

import com.cw.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2024-08-23
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    Goods queryGoodsById(Integer id);
}
