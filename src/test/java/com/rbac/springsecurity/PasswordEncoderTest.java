package com.rbac.springsecurity;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordEncoderTest {
    private final static Logger LOG = LoggerFactory.getLogger(PasswordEncoderTest.class);
    private static String password="123456";

    @Test
    public void testNoOpPasswordEncoder(){
        PasswordEncoder instance = NoOpPasswordEncoder.getInstance();
        String encode = instance.encode(password);
        boolean matches = instance.matches(password, encode);
        LOG.info("testNoOpPasswordEncoder {} match {} is {}",password,encode,matches);
    }
    @Test
    public void testBCryptPasswordEncoder(){
        PasswordEncoder instance = new BCryptPasswordEncoder();
        String encode = instance.encode(password);
        boolean matches = instance.matches(password, encode);
        LOG.info("testBCryptPasswordEncoder {} match {} is {}",password,encode,matches);
    }

    @Test
    public void testPasswordEncoderFactories(){
        PasswordEncoder instance =  PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = instance.encode(password);
        boolean matches = instance.matches(password, encode);
        LOG.info("testPasswordEncoderFactories {} match {} is {}",password,encode,matches);
    }
}
