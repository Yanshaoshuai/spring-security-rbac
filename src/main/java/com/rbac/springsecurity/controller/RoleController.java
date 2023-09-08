package com.rbac.springsecurity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rbac.springsecurity.common.PageRequest;
import com.rbac.springsecurity.common.Result;
import com.rbac.springsecurity.pojo.dto.RoleDTO;
import com.rbac.springsecurity.pojo.entity.Role;
import com.rbac.springsecurity.pojo.vo.RoleDetailVO;
import com.rbac.springsecurity.pojo.vo.UserDetailVO;
import com.rbac.springsecurity.service.RoleService;
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
@RequestMapping("role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("{id}")
    public Result<RoleDetailVO> getRoleDetailById(@PathVariable Long id) {
        return Result.ok(roleService.getRoleDetailById(id));
    }
    @GetMapping
    public Result<Page<Role>> getRole(PageRequest pageRequest) {
        Page<Role> page = new Page<>(pageRequest.getPageNumber(), pageRequest.getPageSize());
        return Result.ok(roleService.page(page));
    }
    @GetMapping("keyword")
    public Result<List<Role>> getByKeyword(String keyword) {
        QueryWrapper<Role> wrapper=new QueryWrapper<>();
        wrapper.like("name",keyword);
        return Result.ok(roleService.list(wrapper));
    }
    @DeleteMapping("{id}")
    public Result<Boolean> deleteRole(@PathVariable Long id) {
        return Result.ok(roleService.removeById(id));
    }

    @PostMapping
    public Result<Boolean> addRoleAndPermission(@RequestBody RoleDTO role) {
        return Result.ok(roleService.addRoleAndPermission(role));
    }

    @PutMapping
    public Result<Boolean> updateRoleAndPermission(@RequestBody RoleDTO role) {
        return Result.ok(roleService.updateRoleAndPermission(role));
    }
}
