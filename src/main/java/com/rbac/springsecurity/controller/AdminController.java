package com.rbac.springsecurity.controller;

import com.rbac.springsecurity.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {
    @RequestMapping
    public Result<String> getUser() {
        return Result.ok("admin");
    }
}
