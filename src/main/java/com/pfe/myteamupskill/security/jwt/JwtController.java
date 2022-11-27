package com.pfe.myteamupskill.security.jwt;

import com.pfe.myteamupskill.authentication.MyUser;
import com.pfe.myteamupskill.exceptions.AppException;
import com.pfe.myteamupskill.payload.request.JwtRequest;
import com.pfe.myteamupskill.payload.response.JwtResponse;
import com.pfe.myteamupskill.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

  static Logger logger = LoggerFactory.getLogger(JwtController.class);

  @PostMapping("/authenticate")
  public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {
    Authentication authentication = logUser(jwtRequest.getLogin(), jwtRequest.getPassword());
    if (authentication == null){
      SecurityContextHolder.clearContext();
      throw new AppException("Authentication failed",HttpStatus.FORBIDDEN);
    }
    String jwt = jwtUtils.generateToken(authentication);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Authorization", "Bearer " + jwt);
    Object principal = authentication.getPrincipal();
    logger.info(((MyUser) principal).getUsername());
    return new ResponseEntity<>(
             new JwtResponse(
                      ((MyUser) principal).getUsername(),
                      ((MyUser) principal).getId(),
                      ((MyUser) principal).getFirstname(),
                      ((MyUser) principal).getLastname(),
                      ((MyUser) principal).getAuthorities()), httpHeaders, HttpStatus.OK);
  }

  public Authentication logUser(String login, String password) {
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(new UsernamePasswordAuthenticationToken(login, password));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return authentication;
  }


}
