package com.cw.mapper;

import com.cw.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2024-08-19
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User queryUserById(Integer id);
}
