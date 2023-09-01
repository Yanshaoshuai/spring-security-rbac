package com.rbac.springsecurity.controller;

import com.rbac.springsecurity.common.Result;
import com.rbac.springsecurity.pojo.entity.User;
import com.rbac.springsecurity.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping
    public Result<List<User>> getUser(){
        return Result.ok(userService.list());
    }
}
