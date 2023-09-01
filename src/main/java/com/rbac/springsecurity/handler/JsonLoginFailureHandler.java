package com.rbac.springsecurity.handler;

import com.alibaba.fastjson2.JSON;
import com.rbac.springsecurity.common.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class JsonLoginFailureHandler implements AuthenticationFailureHandler {
    private final static Logger LOG = LoggerFactory.getLogger(AuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(Result.error(HttpStatus.FORBIDDEN)));
        LOG.error("login fail:",exception);
    }
}
