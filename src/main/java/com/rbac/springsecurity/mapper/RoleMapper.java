package com.rbac.springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rbac.springsecurity.pojo.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select * from role where id in (select rid from user_role where uid =#{userId})")
    List<Role> getRolesByUserId(@Param("userId") Long userId);
    @Delete("DELETE FROM role_permission WHERE rid=#{id}")
    void deleteRolePermissionByRoleId(Long id);
    @Insert("INSERT INTO role_permission (rid, pid) VALUES (#{id},#{permissionId})")
    void insertRolePermission(Long id, Long permissionId);
}
