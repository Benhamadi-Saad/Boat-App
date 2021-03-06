package com.example.boatApp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(1)
@Profile("!swagger")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected static final String USER_Role = "USER";
    protected static final String ADMIN_ROLE = "ADMIN";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}user")
                .roles(USER_Role)
                .and()
                .withUser("admin")
                .password("{noop}admin")
                .roles(ADMIN_ROLE);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").hasRole(USER_Role)
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .and()
                .logout().logoutUrl("/logout");
    }
}
