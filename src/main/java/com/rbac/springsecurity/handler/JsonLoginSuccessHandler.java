package com.rbac.springsecurity.handler;

import com.alibaba.fastjson2.JSON;
import com.rbac.springsecurity.common.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.stream.Collectors;

public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(
                        Result.ok(authentication.getAuthorities()
                                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                )
        );
    }
}
