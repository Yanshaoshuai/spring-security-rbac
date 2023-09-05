package com.rbac.springsecurity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rbac.springsecurity.common.PageRequest;
import com.rbac.springsecurity.common.Result;
import com.rbac.springsecurity.pojo.dto.UpdateUserDTO;
import com.rbac.springsecurity.pojo.entity.User;
import com.rbac.springsecurity.pojo.vo.UserDetailVO;
import com.rbac.springsecurity.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Result<Page<User>> getUser(PageRequest pageRequest) {
        Page<User> page = new Page<>(pageRequest.getPageNumber(), pageRequest.getPageSize());
        return Result.ok(userService.page(page));
    }

    @PostMapping
    public Result<Boolean> addUser(User user) {
        return Result.ok(userService.save(user));
    }

    @PutMapping
    public Result<Boolean> updateUser(@RequestBody UpdateUserDTO user) {
        return Result.ok(userService.updateUserAndRoleById(user));
    }

    @GetMapping("{id}")
    public Result<UserDetailVO> getUserDetailById(@PathVariable Long id) {
        return Result.ok(userService.getUserDetailById(id));
    }
}
