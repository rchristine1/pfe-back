package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.models.Manager;
import com.pfe.myteamupskill.models.TeamMember;
import com.pfe.myteamupskill.security.jwt.JwtController;
import com.pfe.myteamupskill.security.jwt.JwtFilter;
import com.pfe.myteamupskill.security.jwt.JwtUtils;
import com.pfe.myteamupskill.services.ManagerService;
import com.pfe.myteamupskill.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class ManagerController {
  @Autowired
  ManagerService managerService;

  @Autowired
  JwtController jwtController;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UserService userService;

  @GetMapping(value = "/managers/{managerId}")
  //@PreAuthorize("hasRole('TEAMLEADER')")
  public ResponseEntity teammember(Principal principal, @PathVariable("managerId") String managerId) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    Integer managerIdValue = Integer.valueOf(managerId);
    Manager managerSelected = managerService.findById(managerIdValue);
    if (managerSelected == null) {
      return new ResponseEntity("Manager not existing", HttpStatus.NOT_FOUND);
    }
    ManagerDto managerDto = new ManagerDto();
    managerDto.setTeam(managerSelected.getTeam().getDepartment()
            +'-'+managerSelected.getTeam().getDivision()
            +'-'+managerSelected.getTeam().getName());

    return new ResponseEntity(managerDto, HttpStatus.OK);
  }


  @PostMapping("/managers/")
  @PreAuthorize("hasRole('TEAMLEADER')")
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
