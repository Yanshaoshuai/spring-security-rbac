package com.rbac.springsecurity.config;


import com.rbac.springsecurity.pojo.entity.Permission;
import com.rbac.springsecurity.pojo.entity.User;
import com.rbac.springsecurity.pojo.entity.UserPrincipal;
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
        if(authentication.get()==null){
            return new AuthorizationDecision(false);
        }
        Object principal = authentication.get().getPrincipal();
        UserPrincipal userPrincipal = null;
        if(principal instanceof User){
            userPrincipal=(UserPrincipal) principal;
        }
        boolean hasPermission = false;
        if(userPrincipal!=null){
            for (Permission permission : userPrincipal.getPermissions()) {
                AntPathMatcher matcher = new AntPathMatcher();
                if (permission != null
                        && matcher.match(permission.getUrl(), request.getRequestURI())
                        && methodMatch(permission, request.getMethod())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        LOG.info("user {} path {} method {} match result {}", userPrincipal, request.getRequestURI(), request.getMethod(), hasPermission);
        return new AuthorizationDecision(hasPermission);
    }

    private static boolean methodMatch(Permission permission,String method) {
        return method.equalsIgnoreCase(permission.getMethod())||"ALL".equalsIgnoreCase(permission.getMethod());
    }
}

