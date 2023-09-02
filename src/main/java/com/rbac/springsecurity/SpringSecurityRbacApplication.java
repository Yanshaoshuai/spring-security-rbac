package com.rbac.springsecurity;

import com.rbac.springsecurity.pattern.event.spring.CustomSpringEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityRbacApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityRbacApplication.class, args);
    }
}
