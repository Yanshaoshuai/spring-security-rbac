package com.rbac.springsecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rbac.springsecurity.mapper.UserMapper;
import com.rbac.springsecurity.pojo.entity.Permission;
import com.rbac.springsecurity.pojo.entity.Role;
import com.rbac.springsecurity.pojo.entity.User;
import com.rbac.springsecurity.service.PermissionService;
import com.rbac.springsecurity.service.RoleService;
import com.rbac.springsecurity.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService , UserDetailsService {
    private final UserMapper userMapper;

    private final RoleService roleService;

    private final PermissionService permissionService;

    public UserServiceImpl(UserMapper userMapper, RoleService roleService, PermissionService permissionService) {
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user = userMapper.selectOne(userQueryWrapper);
        if(ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("user not found");
        }
        List<Role> roles = roleService.getUserRoles(user.getId());
        List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        List<Permission> permissions = permissionService.getPermissionsByRoleIds(roleIds);
        user.setRoles(roles);
        user.setPermissions(permissions);
        Set<GrantedAuthority> authorities=new HashSet<>();
        authorities.addAll(AuthorityUtils.createAuthorityList(permissions.stream().map(Permission::getName).collect(Collectors.toList())));
        authorities.addAll(AuthorityUtils.createAuthorityList(roles.stream().map(role -> "ROLE_" + role.getName()).collect(Collectors.toList())));
        user.setAuthorities(authorities);
        return user;
    }
}
