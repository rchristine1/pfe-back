package com.pfe.myteamupskill.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {


  @Autowired
  JwtUtils jwtUtils;

  public static final String AUTHORIZATION_HEADER = "Authorization";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    String jwt = resolveToken(request);
    if (StringUtils.hasText(jwt)) {
      try {
        Authentication authentication = jwtUtils.getAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (RuntimeException e) {
        SecurityContextHolder.clearContext();
        throw e;
      }
    }

    chain.doFilter(request, response);
  }


  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

}
