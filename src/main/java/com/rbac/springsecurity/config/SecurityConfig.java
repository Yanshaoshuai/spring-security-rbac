package com.rbac.springsecurity.config;

import com.rbac.springsecurity.filter.JsonLoginFilter;
import com.rbac.springsecurity.handler.CustomAccessDeniedHandler;
import com.rbac.springsecurity.handler.JsonLoginFailureHandler;
import com.rbac.springsecurity.handler.JsonLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public JsonLoginFilter loginFilter(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        JsonLoginFilter jsonLoginFilter = new JsonLoginFilter();
        jsonLoginFilter.setAuthenticationSuccessHandler(new JsonLoginSuccessHandler());
        jsonLoginFilter.setAuthenticationFailureHandler(new JsonLoginFailureHandler());
        jsonLoginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        jsonLoginFilter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        return jsonLoginFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JsonLoginFilter jsonLoginFilter, CustomAuthorizationManager authorizationManager) throws Exception {
        http.authorizeHttpRequests(
                authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/test/**").hasRole("admin")
                                .anyRequest().access(authorizationManager)
        );
        http.formLogin(loginConfigurer -> loginConfigurer.loginProcessingUrl("/login").failureForwardUrl("/login"));
        // http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jsonLoginFilter, UsernamePasswordAuthenticationFilter.class);
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
}
