package com.rbac.springsecurity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rbac.springsecurity.pojo.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> getUserRoles(Long userId);
}
