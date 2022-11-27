package com.pfe.myteamupskill.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class JwtUtils {

  //long JWT_VALIDITY = 5 * 60 * 60;
  long JWT_VALIDITY = 20 * 60 ;

  private static final String AUTHORITIES_KEY = "sub";
  private static final String ROLES_KEY = "auth";

  @Value("${jwt.secret}")
  String secret;

  public String generateToken(Authentication authentication) {
    String authorities = authentication.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));


    return Jwts.builder()
            .claim(ROLES_KEY,authorities)
            .setSubject(authentication.getName())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();

    Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(ROLES_KEY).toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

    User principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

}
