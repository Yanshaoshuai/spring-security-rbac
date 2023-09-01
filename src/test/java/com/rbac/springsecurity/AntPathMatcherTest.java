package com.rbac.springsecurity;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.AntPathMatcher;

@SpringBootTest
public class AntPathMatcherTest {
    private final static Logger LOG = LoggerFactory.getLogger(AntPathMatcherTest.class);
    @Test
    public void testAntPathMatcher(){
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        LOG.info("/user/? match /user/1 -->{}",antPathMatcher.match("/user/?", "/user/1"));
        LOG.info("/user/? match /user/11 -->{}",antPathMatcher.match("/user/?", "/user/11"));
        LOG.info("/user/* match /user -->{}",antPathMatcher.match("/user/*", "/user"));
        LOG.info("/user/* match /user/1 -->{}",antPathMatcher.match("/user/*", "/user/1"));
        LOG.info("/user/* match /user/11 -->{}",antPathMatcher.match("/user/*", "/user/11"));
        LOG.info("/user/* match /user/1/2 -->{}",antPathMatcher.match("/user/*", "/user/1/2"));
        LOG.info("/user/** match /user/1/2 -->{}",antPathMatcher.match("/user/**", "/user/1/2"));
    }
}
