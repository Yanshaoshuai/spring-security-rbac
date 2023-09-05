package com.rbac.springsecurity.config;

import com.rbac.springsecurity.filter.JsonLoginFilter;
import com.rbac.springsecurity.handler.CustomAccessDeniedHandler;
import com.rbac.springsecurity.handler.JsonLoginFailureHandler;
import com.rbac.springsecurity.handler.JsonLoginSuccessHandler;
import com.rbac.springsecurity.pattern.event.spring.CustomSpringEventPublisher;
import com.rbac.springsecurity.repository.RedisContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

@Configuration
public class SecurityConfig {

    /**
     * for event test
     */
    private final CustomSpringEventPublisher customSpringEventPublisher;

    public SecurityConfig(CustomSpringEventPublisher customSpringEventPublisher) {
        this.customSpringEventPublisher = customSpringEventPublisher;
    }

    @Bean
    public JsonLoginFilter loginFilter(AuthenticationConfiguration authenticationConfiguration, RedisContextRepository repository) throws Exception {
        customSpringEventPublisher.publishCustomEvent("JsonLoginFilter init");
        JsonLoginFilter jsonLoginFilter = new JsonLoginFilter();
        jsonLoginFilter.setAuthenticationSuccessHandler(new JsonLoginSuccessHandler());
        jsonLoginFilter.setAuthenticationFailureHandler(new JsonLoginFailureHandler());
        jsonLoginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        jsonLoginFilter.setSecurityContextRepository(repository);
        return jsonLoginFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JsonLoginFilter jsonLoginFilter, CustomAuthorizationManager authorizationManager, RedisContextRepository repository) throws Exception {
        http.authorizeHttpRequests(
                authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/test/**").hasRole("admin")
                                .anyRequest().access(authorizationManager)
        );
        http.formLogin(loginConfigurer -> loginConfigurer.loginProcessingUrl("/login").failureForwardUrl("/login"));
        // http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jsonLoginFilter, UsernamePasswordAuthenticationFilter.class);
        http.securityContext(
                (securityContext) -> securityContext
                        .securityContextRepository(new DelegatingSecurityContextRepository(
                                        new RequestAttributeSecurityContextRepository(),
                                        new HttpSessionSecurityContextRepository(),
                                        repository
                                )
                        ));

        // 先检查验证码
        // http.addFilterBefore(new CaptchaFilter(), JsonLoginFilter.class);
        // 异常处理
        http.exceptionHandling(e -> e.accessDeniedHandler(new CustomAccessDeniedHandler()));
        // 关闭跨域漏洞防御
        http.csrf(t -> t.disable());
        // 关闭跨域拦截
        http.cors(t -> t.disable());
        // 退出 删除session
        // http.logout(logoutConfigurer -> logoutConfigurer.invalidateHttpSession(true));
        return http.build();
    }


    @Bean
    public PasswordEncoder getPwdEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_admin > ROLE_user";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
}
