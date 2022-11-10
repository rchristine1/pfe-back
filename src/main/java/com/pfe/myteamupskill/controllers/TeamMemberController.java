package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.models.*;
import com.pfe.myteamupskill.security.jwt.JwtController;
import com.pfe.myteamupskill.security.jwt.JwtFilter;
import com.pfe.myteamupskill.security.jwt.JwtUtils;
import com.pfe.myteamupskill.services.TeamMemberService;
import com.pfe.myteamupskill.services.UserService;
import com.pfe.myteamupskill.services.UserSkillService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class TeamMemberController {
  @Autowired
  TeamMemberService teamMemberService;

  @Autowired
  UserService userService;

  @Autowired
  UserSkillService userSkillService;

  @Autowired
  JwtController jwtController;

  @Autowired
  JwtUtils jwtUtils;

  @GetMapping(value = "/teammembers/{teamMemberId}")
  public ResponseEntity teammember(Principal principal, @PathVariable("teamMemberId") String teamMemberId) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    Integer teamMemberIdValue = Integer.valueOf(teamMemberId);
    TeamMember teamMemberSelected = teamMemberService.getTeamMember(teamMemberIdValue);
    if (teamMemberSelected == null) {
      return new ResponseEntity("TeamMember not existing", HttpStatus.NOT_FOUND);
    }
    TeamMemberDTO teamMemberDTO = new TeamMemberDTO();
    teamMemberDTO.setManager(teamMemberSelected.getManager());
    teamMemberDTO.setStatusCurrentCampaign(teamMemberSelected.getStatusCurrentCampaign());
    teamMemberDTO.setStatusVolunteerTrainer(teamMemberSelected.getStatusVolunteerTrainer());
    teamMemberDTO.setStatusLastCampaign(teamMemberSelected.getStatusLastCampaign());

    return new ResponseEntity(teamMemberDTO, HttpStatus.OK);
  }

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

  @PatchMapping(value = "/teammembers/{teamMemberId}/statusCurrentCampaign")
  //@PreAuthorize()
  public ResponseEntity updateStatusCurrentCampaign(Principal principal,
                                                    @PathVariable("teamMemberId") String teamMemberId,
                                                    @Valid @RequestBody TeamMemberCampaignDTO statusCampaignToUpdate) {
    Integer userConnectedIdP = userService.getUserConnectedId(principal);
    User existingUserP = userService.getUser(userConnectedIdP);
    if (existingUserP == null) {
      return new ResponseEntity("User unknown", HttpStatus.NOT_FOUND);
    }
    TeamMember teamMemberToPatch = teamMemberService.getTeamMember(Integer.valueOf(teamMemberId));
    if (teamMemberToPatch == null) {
      return new ResponseEntity("TeamMember not existing", HttpStatus.NOT_FOUND);
    }
    List<UserSkill> userSkillsMarked = userSkillService.listUserSkillsMarkedByTeamMember(teamMemberToPatch);
    List<UserSkill> userSkills = userSkillService.listUserSkills(teamMemberToPatch);
    if (userSkillsMarked.size() == userSkills.size()) {
      try {
        teamMemberToPatch.setStatusCurrentCampaign(statusCampaignToUpdate.getStatusUserCampaign());
        teamMemberToPatch = teamMemberService.updateTeamMemberCampaign(teamMemberToPatch);
        TeamMemberDTO teamMemberDTO = new TeamMemberDTO();
        teamMemberDTO.setManager(teamMemberToPatch.getManager());
        teamMemberDTO.setStatusCurrentCampaign(teamMemberToPatch.getStatusCurrentCampaign());
        teamMemberDTO.setStatusVolunteerTrainer(teamMemberToPatch.getStatusVolunteerTrainer());
        teamMemberDTO.setStatusLastCampaign(teamMemberToPatch.getStatusLastCampaign());
        return new ResponseEntity<>(teamMemberDTO, HttpStatus.OK);
      } catch (IllegalArgumentException exception) {
        return new ResponseEntity<>("Bad Status Campaign", HttpStatus.BAD_REQUEST);
      }
    }
    return new ResponseEntity<>("UserSkills are not All Marked", HttpStatus.NOT_ACCEPTABLE);
  }
}



