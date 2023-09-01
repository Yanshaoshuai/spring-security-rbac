package com.rbac.springsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rbac.springsecurity.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
