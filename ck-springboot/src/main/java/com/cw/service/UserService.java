package com.cw.service;

import com.cw.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2024-08-19
 */
public interface UserService extends IService<User> {
    User queryUserById(Integer id);
}
