package com.rbac.springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rbac.springsecurity.pojo.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    @Select("select * from permission where id in (select pid from user_role where rid =#{roleId})")
    List<Permission> getPermissionsByRoleId(String roleId);
    List<Permission> getPermissionsByRoleIds(List<Long> roleIds);

    @Select("select * from permission where name =#{name}")
    Permission getPermissionsByName(String name);
}
