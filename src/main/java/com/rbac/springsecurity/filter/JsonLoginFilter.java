package com.rbac.springsecurity.filter;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbac.springsecurity.handler.JsonLoginFailureHandler;
import com.rbac.springsecurity.handler.JsonLoginSuccessHandler;
import com.rbac.springsecurity.pojo.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JsonLoginFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        User user = obtainUser(request);
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(user.getUsername(), user.getPassword());
        setDetails(request, authRequest);
        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.contains(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            return false;
        }
        return super.requiresAuthentication(request, response);
    }

    @Override
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        super.setAuthenticationSuccessHandler(new JsonLoginSuccessHandler());
    }

    @Override
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler(new JsonLoginFailureHandler());
    }

    private User obtainUser(HttpServletRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.reader().readValue(request.getReader(), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
