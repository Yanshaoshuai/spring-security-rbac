package com.rbac.springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rbac.springsecurity.pojo.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Set;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Insert("INSERT INTO user_role (uid, rid) VALUES (#{uid},#{rid})")
    void insertUserRole(@Param("uid") Long uid,@Param("rid") Long rid);

    @Delete("DELETE FROM user_role WHERE uid=#{id}")
    void deleteUserRoleByUserId(Long id);
}
