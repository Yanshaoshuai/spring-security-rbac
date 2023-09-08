package com.rbac.springsecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rbac.springsecurity.mapper.PermissionMapper;
import com.rbac.springsecurity.pojo.entity.Permission;
import com.rbac.springsecurity.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<Permission> getRolePermissions(Long roleId) {
        return permissionMapper.getPermissionsByRoleId(roleId);
    }

    @Override
    public List<Permission> getPermissionsByRoleIds(List<Long> roleIds) {
        return permissionMapper.getPermissionsByRoleIds(roleIds);
    }

    @Override
    public Permission getByName(String name) {
        return permissionMapper.getPermissionsByName(name);
    }
}
