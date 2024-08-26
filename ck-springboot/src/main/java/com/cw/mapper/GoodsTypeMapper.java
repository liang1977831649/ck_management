package com.cw.mapper;

import com.cw.entity.GoodsType;
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
public interface GoodsTypeMapper extends BaseMapper<GoodsType> {
    GoodsType queryGoodsTypeById(Integer id);
    GoodsType queryGoodsTypeByGoodsId(Integer id);
}
