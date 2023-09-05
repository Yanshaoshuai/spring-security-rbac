package com.rbac.springsecurity.repository;

import com.rbac.springsecurity.util.ObjectSerializer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisContextRepository implements SecurityContextRepository {
    private static final String keyPrefix = "SPRING_SECURITY_SESSION_";
    private static final Long expire = 600L;
    private final RedissonClient redissonClient;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    public RedisContextRepository(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        return loadDeferredContext(requestResponseHolder.getRequest()).get();
    }

    @Override
    public DeferredSecurityContext loadDeferredContext(HttpServletRequest request) {
        SecurityContext redisContext = getRedisContext(request);
        return new DeferredSecurityContext() {
            @Override
            public boolean isGenerated() {
                return redisContext != null;
            }

            @Override
            public SecurityContext get() {
                return redisContext;
            }
        };
    }

    private SecurityContext getRedisContext(HttpServletRequest request) {
        RBucket<String> bucket = redissonClient.getBucket(keyPrefix + request.getSession().getId());
        return (SecurityContext) ObjectSerializer.deserialize(bucket.get());
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        RBucket<String> bucket = redissonClient.getBucket(keyPrefix + request.getSession().getId());
        bucket.set(ObjectSerializer.serialize(context), Duration.ofSeconds(expire));
        securityContextHolderStrategy.setContext(context);
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return getRedisContext(request) != null;
    }
}
