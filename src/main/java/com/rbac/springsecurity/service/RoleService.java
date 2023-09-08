package com.rbac.springsecurity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rbac.springsecurity.pojo.dto.RoleDTO;
import com.rbac.springsecurity.pojo.entity.Role;
import com.rbac.springsecurity.pojo.vo.RoleDetailVO;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> getUserRoles(Long userId);

    RoleDetailVO getRoleDetailById(Long id);

    Boolean addRoleAndPermission(RoleDTO role);

    Boolean updateRoleAndPermission(RoleDTO role);
}
