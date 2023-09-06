package com.rbac.springsecurity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rbac.springsecurity.pojo.dto.UpdateUserDTO;
import com.rbac.springsecurity.pojo.entity.User;
import com.rbac.springsecurity.pojo.vo.UserDetailVO;

public interface UserService extends IService<User> {
    UserDetailVO getUserDetailById(Long id);

    Boolean updateUserAndRoleById(UpdateUserDTO user);

    Long saveUserAndRole(UpdateUserDTO user);
}
