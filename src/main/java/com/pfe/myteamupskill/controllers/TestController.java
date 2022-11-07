package com.pfe.myteamupskill.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/test")
public class TestController {
@GetMapping("/all")
  public String allAccess(){
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER')")
  public String userAccess(){
    return "User Content.";
  }

  @GetMapping("/teammember")
  @PreAuthorize("hasRole('TEAMMEMBER')")
  public String teammemberAccess(){
    return "Teammember Board.";
  }

  @GetMapping("/teamleader")
  @PreAuthorize("hasRole('TEAMLEADER') or hasRole('MANAGER')")
  public String teamleaderAccess(){
    return "TeamLeader Board.";
  }

  @GetMapping("/manager")
  @PreAuthorize("hasRole('MANAGER')")
  public String managerAccess(){
    return "Manager Board.";
  }
}
