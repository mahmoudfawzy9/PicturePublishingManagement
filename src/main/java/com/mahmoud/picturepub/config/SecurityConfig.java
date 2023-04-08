package com.mahmoud.picturepub.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

@Autowired
private DataSource dataSource;


@Override
protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/user/**").hasAuthority("ROLE_USER")
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.jdbcAuthentication()
                        .dataSource(dataSource)
                        .passwordEncoder(passwordEncoder())
                        .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
                        .authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");

                // This was added just for testing purpose (user:admin, password: admin123)  you can use these credentials for login

                auth.inMemoryAuthentication()
                        .withUser("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN")
            .and()
            .withUser("user").password(passwordEncoder().encode("user")).roles("USER");
        }



@Bean
public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        }
        }