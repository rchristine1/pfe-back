package com.pfe.myteamupskill.security.jwt;

import com.pfe.myteamupskill.payload.request.JwtRequest;
import com.pfe.myteamupskill.payload.response.JwtResponse;
import com.pfe.myteamupskill.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {
  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UserService userService;

  @Autowired
  AuthenticationManagerBuilder authenticationManagerBuilder;

  @PostMapping("/authenticate")
  public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {
    Authentication authentication = logUser(jwtRequest.getLogin(), jwtRequest.getPassword());
    System.out.println("Controller Authentication "+jwtRequest.getLogin());
    String jwt = jwtUtils.generateToken(authentication);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Authorization", "Bearer " + jwt);
    Object principal = authentication.getPrincipal();
    Integer id = userService.findOneByLogin(((User) principal).getUsername()).getId();
    System.out.println("Authenticate"+id);
    return new ResponseEntity<>(new JwtResponse(((User) principal).getUsername(),id), httpHeaders, HttpStatus.OK);
  }

  public Authentication logUser(String login, String password) {
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(new UsernamePasswordAuthenticationToken(login, password));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    System.out.println("Controller logUser");
    return authentication;
  }
}
