package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.models.*;
import com.pfe.myteamupskill.security.jwt.JwtController;
import com.pfe.myteamupskill.security.jwt.JwtFilter;
import com.pfe.myteamupskill.security.jwt.JwtUtils;
import com.pfe.myteamupskill.services.TeamMemberService;
import com.pfe.myteamupskill.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class TeamMemberController {
  @Autowired
  TeamMemberService teamMemberService;

  @Autowired
  UserService userService;

  @Autowired
  JwtController jwtController;

  @Autowired
  JwtUtils jwtUtils;


  @PostMapping("/teammembers/")
  public ResponseEntity add(@Valid @RequestBody TeamMember userEntity) {

    TeamMember existingUser = teamMemberService.findOneByLogin(userEntity.getLogin());
    if (existingUser != null) {
      return new ResponseEntity("User already existing", HttpStatus.BAD_REQUEST);
    }
    TeamMember user = teamMemberService.saveUser(userEntity);
    Authentication authentication = jwtController.logUser(userEntity.getLogin(), userEntity.getPassword());
    String jwt = jwtUtils.generateToken(authentication);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
    return new ResponseEntity<>(user, httpHeaders, HttpStatus.OK);
  }

  @PutMapping(value = "/teammembers/{teamMemberId}")
  public ResponseEntity update(Principal principal,@PathVariable("teamMemberId") String teamMemberId,@RequestParam(required = false) EStatusUserCampaign status) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    User existingUser = userService.getUser(userConnectedId);
    if (existingUser == null) {
      return new ResponseEntity("User unknown", HttpStatus.BAD_REQUEST);
    }
    TeamMember teamMemberToUpdate = teamMemberService.getTeamMember(Integer.valueOf(teamMemberId));
    System.out.println("Update "+teamMemberToUpdate.getFirstName());
    if (teamMemberToUpdate == null) {
      return new ResponseEntity("TeamMember unknown", HttpStatus.BAD_REQUEST);
    }
    if (status != null) {
      TeamMember teamMemberCampaign = teamMemberService.updateTeamMemberCampaign(teamMemberToUpdate, status);
      System.out.println("Update Campaign " + teamMemberCampaign.getStatusCurrentCampaign());
      return new ResponseEntity<>(teamMemberCampaign, HttpStatus.OK);
    } else{
      return new ResponseEntity<>((TeamMember)existingUser, HttpStatus.OK);
    }

  }
}
