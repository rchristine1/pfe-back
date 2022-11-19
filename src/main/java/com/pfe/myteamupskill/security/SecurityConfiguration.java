package com.pfe.myteamupskill.security;

import com.pfe.myteamupskill.security.jwt.JwtFilter;
import com.pfe.myteamupskill.security.jwt.JwtUtils;
import com.pfe.myteamupskill.security.jwt.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration{

  @Autowired
  RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  @Autowired
  JwtFilter jwtFilter;

  @Autowired
  JwtUtils jwtUtils;

  @Bean
  SecurityFilterChain web(HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(restAuthenticationEntryPoint)
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/static/**/").permitAll()
            .antMatchers("/authenticate").permitAll()
            .antMatchers("/isConnected").permitAll()
            .antMatchers("/v3/api-docs/**").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .anyRequest().authenticated();
     return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
