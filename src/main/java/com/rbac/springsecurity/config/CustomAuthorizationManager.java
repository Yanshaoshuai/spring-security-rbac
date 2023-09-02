package com.rbac.springsecurity.config;


import com.rbac.springsecurity.pojo.entity.Permission;
import com.rbac.springsecurity.pojo.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.function.Supplier;

@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final static Logger LOG = LoggerFactory.getLogger(CustomAuthorizationManager.class);

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        Object principal = authentication.get().getPrincipal();
        User user = null;
        if(principal instanceof User){
            user=(User) principal;
        }
        boolean hasPermission = false;
        if(user!=null){
            for (Permission permission : user.getPermissions()) {
                AntPathMatcher matcher = new AntPathMatcher();
                if (permission != null && matcher.match(permission.getUrl(), request.getRequestURI()) && request.getMethod().equalsIgnoreCase(permission.getMethod())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        LOG.info("user {} path {} method {} match result {}", principal, request.getRequestURI(), request.getMethod(), hasPermission);
        return new AuthorizationDecision(hasPermission);
    }
}

