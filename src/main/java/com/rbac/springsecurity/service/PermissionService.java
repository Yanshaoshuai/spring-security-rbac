package com.rbac.springsecurity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rbac.springsecurity.pojo.entity.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    List<Permission> getRolePermissions(String roleId);

    List<Permission> getPermissionsByRoleIds(List<Long> roleIds);

    Permission getByName(String name);
}
