package com.it.elderhub.controller;

import com.it.elderhub.common.Result;
import com.it.elderhub.entity.User;
import com.it.elderhub.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/users")
    public Result<List<User>> listUsers() {
        List<User> users = userMapper.selectList(null);
        return Result.success(users);
    }
}
