package com.rbac.springsecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rbac.springsecurity.mapper.UserMapper;
import com.rbac.springsecurity.pojo.dto.UpdateUserDTO;
import com.rbac.springsecurity.pojo.entity.Permission;
import com.rbac.springsecurity.pojo.entity.Role;
import com.rbac.springsecurity.pojo.entity.User;
import com.rbac.springsecurity.pojo.entity.UserPrincipal;
import com.rbac.springsecurity.pojo.vo.UserDetailVO;
import com.rbac.springsecurity.service.PermissionService;
import com.rbac.springsecurity.service.RoleService;
import com.rbac.springsecurity.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPrincipal principal=new UserPrincipal();
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user = userMapper.selectOne(userQueryWrapper);
        if(ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("user not found");
        }
        buildUserResult(principal, user);
        List<Role> roles = roleService.getUserRoles(user.getId());
        List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        List<Permission> permissions = permissionService.getPermissionsByRoleIds(roleIds);
        principal.setRoles(roles);
        principal.setPermissions(permissions);
        Set<GrantedAuthority> authorities=new HashSet<>();
        authorities.addAll(AuthorityUtils.createAuthorityList(permissions.stream().map(Permission::getName).distinct().collect(Collectors.toList())));
        authorities.addAll(AuthorityUtils.createAuthorityList(roles.stream().map(role -> "ROLE_" + role.getName()).distinct().collect(Collectors.toList())));
        principal.setAuthorities(authorities);
        return principal;
    }

    private static void buildUserResult(User result, User origin) {
        result.setId(origin.getId());
        result.setUsername(origin.getUsername());
        result.setPassword(origin.getPassword());
        result.setEnabled(origin.getEnabled());
    }

    @Override
    public UserDetailVO getUserDetailById(Long id) {
        User byId = getById(id);
        List<Role> userRoles = roleService.getUserRoles(id);
        UserDetailVO userDetailVo = new UserDetailVO();
        buildUserResult(userDetailVo,byId);
        userDetailVo.setRoles(userRoles);
        return userDetailVo;
    }

    @Transactional
    @Override
    public Boolean updateUserAndRoleById(UpdateUserDTO user) {
        boolean result = updateById(user);
        if(!CollectionUtils.isEmpty(user.getRoles())){
            userMapper.deleteUserRoleByUserId(user.getId());
            Set<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
            for (Long rid:roleIds){
                userMapper.insertUserRole(user.getId(),rid);
            }
        }
        return result;
    }
}
