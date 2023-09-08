package com.rbac.springsecurity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rbac.springsecurity.common.PageRequest;
import com.rbac.springsecurity.common.Result;
import com.rbac.springsecurity.pojo.entity.Permission;
import com.rbac.springsecurity.service.PermissionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("permission")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    @GetMapping("{id}")
    public Result<Permission> getById(@PathVariable Long id) {
        return Result.ok(permissionService.getById(id));
    }
    @GetMapping
    public Result<Page<Permission>> getPermission(PageRequest pageRequest) {
        Page<Permission> page = new Page<>(pageRequest.getPageNumber(), pageRequest.getPageSize());
        return Result.ok(permissionService.page(page));
    }
    @GetMapping("keyword")
    public Result<List<Permission>> getByKeyword(String keyword) {
        QueryWrapper<Permission> wrapper=new QueryWrapper<>();
        wrapper.like("name",keyword);
        return Result.ok(permissionService.list(wrapper));
    }
    @DeleteMapping("{id}")
    public Result<Boolean> deletePermission(@PathVariable Long id) {
        return Result.ok(permissionService.removeById(id));
    }

    @PostMapping
    public Result<Boolean> addPermission(@RequestBody Permission permission) {
        return Result.ok(permissionService.save(permission));
    }

    @PutMapping
    public Result<Boolean> updatePermission(@RequestBody Permission permission) {
        return Result.ok(permissionService.updateById(permission));
    }
}
