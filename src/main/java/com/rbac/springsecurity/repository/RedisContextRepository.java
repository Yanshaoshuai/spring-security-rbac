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

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class RedisContextRepository implements SecurityContextRepository {
    private static final String keyPrefix="SPRING_SECURITY_SESSION_";
    private static final Long expire=600l;
    private final RedissonClient redissonClient;
    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
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
        Supplier<SecurityContext> supplier = () -> getRedisContext(request);
        return new SupplierDeferredSecurityContext(supplier, this.securityContextHolderStrategy);
    }

    private SecurityContext getRedisContext(HttpServletRequest request) {
        RBucket<String> bucket = redissonClient.getBucket(keyPrefix + request.getSession().getId());
        return (SecurityContext) ObjectSerializer.deserialize(bucket.get());
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        RBucket<String> bucket = redissonClient.getBucket(keyPrefix + request.getSession().getId());
        bucket.set(ObjectSerializer.serialize(context), expire, TimeUnit.SECONDS);
        securityContextHolderStrategy.setContext(context);
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return getRedisContext(request) != null;
    }
}
