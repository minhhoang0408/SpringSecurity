package com.example.demo.security;

import com.example.demo.Permission.Permission;
import com.example.demo.Permission.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.example.demo.Permission.Permission.*;
import static com.example.demo.Permission.Roles.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/v1/student/**").hasRole(STUDENT.name())
                .antMatchers(HttpMethod.POST, "/api/v1/course/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers("/api/**").hasRole(ADMIN.name())
                .anyRequest()
                .authenticated();
        http.httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails tom = User.builder()
                .username("tom")
                .password(passwordEncoder().encode("password"))
                .authorities(STUDENT.getGrantedAuthority())
                .build();
        UserDetails hary = User.builder()
                .username("hary")
                .password(passwordEncoder().encode("123456"))
                .authorities(ADMIN.getGrantedAuthority())
                .build();
        return new InMemoryUserDetailsManager(
                tom,
                hary
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
