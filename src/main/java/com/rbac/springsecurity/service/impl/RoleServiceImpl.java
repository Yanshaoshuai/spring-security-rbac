package com.rbac.springsecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rbac.springsecurity.mapper.RoleMapper;
import com.rbac.springsecurity.pojo.entity.Role;
import com.rbac.springsecurity.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public List<Role> getUserRoles(Long userId) {
        return roleMapper.getRolesByUserId(userId);
    }
}
