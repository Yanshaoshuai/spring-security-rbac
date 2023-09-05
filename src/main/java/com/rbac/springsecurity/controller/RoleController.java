package com.rbac.springsecurity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rbac.springsecurity.common.PageRequest;
import com.rbac.springsecurity.common.Result;
import com.rbac.springsecurity.pojo.entity.Role;
import com.rbac.springsecurity.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public Result<List<Role>> getUser(String keyword) {
        QueryWrapper<Role> wrapper=new QueryWrapper<>();
        wrapper.like("name",keyword);
        return Result.ok(roleService.list(wrapper));
    }
}
