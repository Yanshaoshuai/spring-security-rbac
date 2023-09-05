package com.rbac.springsecurity.pojo.dto;

import com.rbac.springsecurity.pojo.entity.Role;
import com.rbac.springsecurity.pojo.entity.User;

import java.util.List;

public class UpdateUserDTO extends User {
     private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
