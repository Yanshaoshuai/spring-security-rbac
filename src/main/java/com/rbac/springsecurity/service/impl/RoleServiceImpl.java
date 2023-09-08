package com.rbac.springsecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rbac.springsecurity.mapper.RoleMapper;
import com.rbac.springsecurity.pojo.dto.RoleDTO;
import com.rbac.springsecurity.pojo.dto.UpdateUserDTO;
import com.rbac.springsecurity.pojo.entity.Permission;
import com.rbac.springsecurity.pojo.entity.Role;
import com.rbac.springsecurity.pojo.vo.RoleDetailVO;
import com.rbac.springsecurity.service.PermissionService;
import com.rbac.springsecurity.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private final RoleMapper roleMapper;

    private final PermissionService permissionService;
    public RoleServiceImpl(RoleMapper roleMapper, PermissionService permissionService) {
        this.roleMapper = roleMapper;
        this.permissionService = permissionService;
    }

    @Override
    public List<Role> getUserRoles(Long userId) {
        return roleMapper.getRolesByUserId(userId);
    }

    @Override
    public RoleDetailVO getRoleDetailById(Long id) {
        Role byId = getById(id);
        List<Permission> rolePermissions = permissionService.getRolePermissions(id);
        RoleDetailVO roleDetailVO=new RoleDetailVO();
        buildResult(roleDetailVO, byId);
        roleDetailVO.setPermissions(rolePermissions);
        return roleDetailVO;
    }
    private void updateUserRole(RoleDTO role) {
        roleMapper.deleteRolePermissionByRoleId(role.getId());
        if (!CollectionUtils.isEmpty(role.getPermissions())) {
            Set<Long> permissionIds = role.getPermissions().stream().map(Permission::getId).collect(Collectors.toSet());
            for (Long permissionId : permissionIds) {
                roleMapper.insertRolePermission(role.getId(), permissionId);
            }
        }
    }
    @Transactional
    @Override
    public Boolean addRoleAndPermission(RoleDTO role) {
        boolean result = save(role);
        updateUserRole(role);
        return result;
    }

    @Override
    public Boolean updateRoleAndPermission(RoleDTO role) {
        boolean result = updateById(role);
        updateUserRole(role);
        return result;
    }

    private static void buildResult(RoleDetailVO roleDetailVO, Role byId) {
        roleDetailVO.setId(byId.getId());
        roleDetailVO.setName(byId.getName());
        roleDetailVO.setDescription(byId.getDescription());
    }
}
