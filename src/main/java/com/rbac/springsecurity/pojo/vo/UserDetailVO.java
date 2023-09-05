package com.rbac.springsecurity.pojo.vo;

import com.rbac.springsecurity.pojo.entity.Role;
import com.rbac.springsecurity.pojo.entity.User;
import java.util.List;

public class UserDetailVO extends User {
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> role) {
        this.roles = role;
    }
}
