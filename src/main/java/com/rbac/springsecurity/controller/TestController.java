package com.rbac.springsecurity.controller;

import com.rbac.springsecurity.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping("hello")
    public Result<String> hello(){
        return Result.ok("hello");
    }
}
