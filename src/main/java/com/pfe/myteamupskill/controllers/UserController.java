package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.authentication.MyUser;
import com.pfe.myteamupskill.payload.response.JwtResponse;
import com.pfe.myteamupskill.security.jwt.JwtController;
import com.pfe.myteamupskill.security.jwt.JwtUtils;
import com.pfe.myteamupskill.services.ManagerService;
import com.pfe.myteamupskill.services.TeamMemberService;
import com.pfe.myteamupskill.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {
  @Autowired
  UserService userService;

  @Autowired
  TeamMemberService teamMemberService;

  @Autowired
  ManagerService managerService;

  @Autowired
  JwtController jwtController;


  @Autowired
  JwtUtils jwtUtils;

  @CrossOrigin()
  @GetMapping(value ="/isConnected")
  public ResponseEntity getUSerConnected() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      System.out.println("condition if");
      return new ResponseEntity((((UserDetails) principal).getUsername()),HttpStatus.OK);
      //UserDetailsImpl userDetails = (UserDetailsImpl) principal;
      //return new ResponseEntity<>(new JwtResponse(((MyUser) principal).getUsername(),((MyUser) principal).getId(),((MyUser) principal).getFirstname(), ((MyUser) principal).getLastname()),HttpStatus.OK);
      //return new ResponseEntity(new JwtResponse(userDetails.getUsername(),userDetails.getId()), HttpStatus.OK);
    }
    return new ResponseEntity("User is not connected", HttpStatus.FORBIDDEN);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/signOut")
  public ResponseEntity<Void> signOut(Principal principal) {
    SecurityContextHolder.clearContext();
    return ResponseEntity.noContent().build();
  }

  }
