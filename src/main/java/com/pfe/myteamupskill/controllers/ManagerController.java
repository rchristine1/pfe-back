package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.models.Manager;
import com.pfe.myteamupskill.security.jwt.JwtController;
import com.pfe.myteamupskill.security.jwt.JwtFilter;
import com.pfe.myteamupskill.security.jwt.JwtUtils;
import com.pfe.myteamupskill.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ManagerController {
  @Autowired
  ManagerService managerService;

  @Autowired
  JwtController jwtController;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/managers/")
  public ResponseEntity add(@Valid @RequestBody Manager userEntity) {

    Manager existingUser = managerService.findOneByLogin(userEntity.getLogin());
    if (existingUser != null) {
      return new ResponseEntity("User already existing", HttpStatus.BAD_REQUEST);
    }
    Manager user = managerService.saveUser(userEntity);
    Authentication authentication = jwtController.logUser(userEntity.getLogin(), userEntity.getPassword());
    String jwt = jwtUtils.generateToken(authentication);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
    return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
  }
}
