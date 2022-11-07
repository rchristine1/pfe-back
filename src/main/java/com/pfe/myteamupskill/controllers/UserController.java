package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.payload.response.JwtResponse;
import com.pfe.myteamupskill.security.jwt.JwtController;
import com.pfe.myteamupskill.security.jwt.JwtUtils;
import com.pfe.myteamupskill.services.ManagerService;
import com.pfe.myteamupskill.services.TeamMemberService;
import com.pfe.myteamupskill.services.UserDetailsImpl;
import com.pfe.myteamupskill.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

  /*@PostMapping("/users/{isTeamMember}")
  public ResponseEntity add(@PathVariable("isTeamMember") Boolean isTeamMember, @Valid @RequestBody User userEntity) {

    User existingUser = userService.findOneByLogin(userEntity.getLogin());
    if(existingUser != null) {
      return new ResponseEntity("User already existing", HttpStatus.BAD_REQUEST);
    }
    Authentication authentication = jwtController.logUser(userEntity.getLogin(), userEntity.getPassword());
    String jwt = jwtUtils.generateToken(authentication);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
    if (isTeamMember != null && isTeamMember == true) {
      TeamMember user = teamMemberService.saveUser(userEntity);
      return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    } else {
      Manager user = managerService.saveUser(userEntity);
      return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
    }

  }*/

  @CrossOrigin()
  @GetMapping(value ="/isConnected")
  public ResponseEntity getUSerConnected() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      return new ResponseEntity(((UserDetails) principal).getUsername(), HttpStatus.OK);
      //UserDetailsImpl userDetails = (UserDetailsImpl) principal;
      //return new ResponseEntity(new JwtResponse(userDetails.getUsername(),userDetails.getId()), HttpStatus.OK);
    }
    return new ResponseEntity("User is not connected", HttpStatus.FORBIDDEN);
  }
}
