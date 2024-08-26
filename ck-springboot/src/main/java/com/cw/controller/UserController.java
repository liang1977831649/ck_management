package com.cw.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cw.entity.Result;
import com.cw.entity.User;
import com.cw.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2024-08-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping
    public Result add(@RequestBody @Valid  User user){
//        System.out.printf("user="+user);
//        return Result.success();
        boolean save = userService.save(user);
        return save?Result.success():Result.error("该用户已存在");
    }
    @DeleteMapping()
    public Result del(Integer id){
        userService.removeById(id);
        return Result.success();
    }
    @GetMapping
    public Result list(@RequestParam(defaultValue = "1",required = false)Integer pageNum,@RequestParam(defaultValue = "5",required = false)Integer pageSize,Integer roleId){
        QueryWrapper<User> query = Wrappers.query();
        query.eq("role_id",roleId);
        Page<User> page = userService.page(new Page<User>(pageNum, pageSize),query);
        //将密码置空
        for (User record : page.getRecords()) {
            record.setPassword(null);
        }
        return Result.success(page);
    }
    @PutMapping
    public Result update(@RequestBody @Valid User user){
        System.out.printf("user="+user);
        userService.updateById(user);
        return Result.success();
    }
    @GetMapping("/search")
    public Result search(@RequestParam String searchContext,@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam Integer roleId){
        QueryWrapper<User> query = Wrappers.query();
        query.like("name",searchContext);
        query.eq("role_id",roleId);
        Page<User> page = userService.page(new Page<>(pageNum,pageSize), query);
        return Result.success(page);
    }
    @PostMapping("/login")
    public Result login(@RequestBody User user){
//        System.out.printf("user="+user);
        QueryWrapper<User> query = Wrappers.query();
        query.eq("no",user.getNo());
        List<User> list = userService.list(query);
        if(list.isEmpty() ||!list.get(0).getPassword().equals(user.getPassword())){
            return Result.error("密码错误");
        }
//      置空密码
        User userById = list.get(0);
        userById.setPassword(null);
        return Result.success(userById);
    }
}
