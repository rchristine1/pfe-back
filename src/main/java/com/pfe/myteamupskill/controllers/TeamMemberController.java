package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.models.*;
import com.pfe.myteamupskill.security.jwt.JwtController;
import com.pfe.myteamupskill.security.jwt.JwtFilter;
import com.pfe.myteamupskill.security.jwt.JwtUtils;
import com.pfe.myteamupskill.services.*;
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
  ManagerService managerService;

  @Autowired
  UserSkillService userSkillService;

  @Autowired
  CampaignService campaignService;

  @Autowired
  JwtController jwtController;

  @Autowired
  JwtUtils jwtUtils;

  @PreAuthorize("hasAnyRole('ROLE_TEAMMEMBER','ROLE_TEAMLEADER')")
  @GetMapping(value = "/teammembers/{teamMemberId}")
  public ResponseEntity teammember(Principal principal, @PathVariable("teamMemberId") String teamMemberId) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    Integer teamMemberIdValue = Integer.valueOf(teamMemberId);
    TeamMember teamMemberSelected = teamMemberService.getTeamMember(teamMemberIdValue);
    if (teamMemberSelected == null) {
      return new ResponseEntity("TeamMember not existing", HttpStatus.NOT_FOUND);
    }
    TeamMemberDto teamMemberDTO = new TeamMemberDto();
    teamMemberDTO.setManager(teamMemberSelected.getManager().getFirstName()+' '+ teamMemberSelected.getManager().getLastName());
    teamMemberDTO.setStatusCurrentCampaign(teamMemberSelected.getStatusCurrentCampaign());
    teamMemberDTO.setStatusVolunteerTrainer(teamMemberSelected.getStatusVolunteerTrainer());
    teamMemberDTO.setStatusLastCampaign(teamMemberSelected.getStatusLastCampaign());
    teamMemberDTO.setFirstname(teamMemberSelected.getFirstName());
    teamMemberDTO.setLastname(teamMemberSelected.getLastName());

    return new ResponseEntity(teamMemberDTO, HttpStatus.OK);
  }


  @PreAuthorize("hasRole('ROLE_TEAMLEADER')")
  @GetMapping(value = "/teammembers/manager/{managerId}")
  @ResponseBody
  public ResponseEntity teammembersStatusCampaign(Principal principal,
                                                  @RequestParam(required = false) String status,
                                                  @PathVariable("managerId") String managerId ) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    List<TeamMember> teamMemberList;

    TeamMemberCampaignDto teamMemberCampaignDto= new TeamMemberCampaignDto();
    teamMemberCampaignDto.setStatusUserCampaign(EStatusUserCampaign.valueOf(status));

    Manager existingManager = managerService.findById(Integer.valueOf(managerId));
    if (existingManager == null) {
      return new ResponseEntity("Manager not found", HttpStatus.NOT_FOUND);
    }

    if (status != null ){
      teamMemberList = teamMemberService.findByStatusCurrentCampaignAndManagerId(
              teamMemberCampaignDto.getStatusUserCampaign(),
              Integer.valueOf(managerId));
    } else {
      teamMemberList = teamMemberService.getTeamMembersByManager(Integer.valueOf(managerId));
    }

    List<TeamMemberDto> teamMemberDTOList = new ArrayList<>();
    for (TeamMember tm :teamMemberList) {
      TeamMemberDto teamMemberDTO = new TeamMemberDto();
      teamMemberDTO.setManager(tm.getManager().getFirstName()+' '+ tm.getManager().getLastName());
      teamMemberDTO.setStatusCurrentCampaign(tm.getStatusCurrentCampaign());
      teamMemberDTO.setStatusVolunteerTrainer(tm.getStatusVolunteerTrainer());
      teamMemberDTO.setStatusLastCampaign(tm.getStatusLastCampaign());
      teamMemberDTO.setId(tm.getId());
      teamMemberDTO.setFirstname(tm.getFirstName());
      teamMemberDTO.setLastname(tm.getLastName());
      teamMemberDTO.setPicture(tm.getPicture());
      teamMemberDTOList.add(teamMemberDTO);
    }
    return new ResponseEntity(teamMemberDTOList, HttpStatus.OK);
  }
  @PreAuthorize("hasAnyRole('ROLE_TEAMLEADER')")
  @PostMapping("/teammembers/")
  public ResponseEntity add(@Valid @RequestBody TeamMember userEntity) {

    TeamMember existingUser = teamMemberService.findOneByLogin(userEntity.getLogin());
    if (existingUser != null) {
      return new ResponseEntity("User already existing", HttpStatus.BAD_REQUEST);
    }
    TeamMember user = teamMemberService.saveUser(userEntity);

    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }
  @PreAuthorize("hasRole('ROLE_TEAMLEADER')")
  @PatchMapping("/teammembers/{teamMemberId}/manager")
  public ResponseEntity updateTeamMember(@PathVariable("teamMemberId") String teamMemberId,
                                         @Valid @RequestBody TeamMemberManagerDto manager) {

    TeamMember teamMemberToPatch = teamMemberService.getTeamMember(Integer.valueOf(teamMemberId));
    if (teamMemberToPatch == null) {
      return new ResponseEntity("User not found", HttpStatus.BAD_REQUEST);
    }

    Manager existingManager = managerService.findById(manager.getId());
    if (existingManager == null) {
      return new ResponseEntity("Manager does not exist", HttpStatus.BAD_REQUEST);
    }

    teamMemberToPatch.setManager(existingManager);
    teamMemberToPatch = teamMemberService.updateTeamMember(teamMemberToPatch);
    TeamMemberDto teamMemberDto = new TeamMemberDto();
    teamMemberDto.setManager(teamMemberToPatch.getManager().getFirstName()+' '+ teamMemberToPatch.getManager().getLastName());
    teamMemberDto.setStatusCurrentCampaign(teamMemberToPatch.getStatusCurrentCampaign());
    teamMemberDto.setStatusVolunteerTrainer(teamMemberToPatch.getStatusVolunteerTrainer());
    teamMemberDto.setStatusLastCampaign(teamMemberToPatch.getStatusLastCampaign());
    return new ResponseEntity<>(teamMemberDto, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_TEAMLEADER') or hasRole('ROLE_TEAMMEMBER')")
  @PatchMapping(value = "/teammembers/{teamMemberId}/statusCurrentCampaign")
  public ResponseEntity updateStatusCurrentCampaign(Principal principal,
                                                    @PathVariable("teamMemberId") String teamMemberId,
                                                    @Valid @RequestBody TeamMemberCampaignDto statusCampaignToUpdate) {
    Integer userConnectedIdP = userService.getUserConnectedId(principal);
    User existingUserP = userService.getUser(userConnectedIdP);
    if (existingUserP == null) {
      return new ResponseEntity("User unknown", HttpStatus.NOT_FOUND);
    }
    TeamMember teamMemberToPatch = teamMemberService.getTeamMember(Integer.valueOf(teamMemberId));
    if (teamMemberToPatch == null) {
      return new ResponseEntity("TeamMember not existing", HttpStatus.NOT_FOUND);
    }
    if (statusCampaignToUpdate.getStatusUserCampaign() == null) {
      return new ResponseEntity("Campaign Status is null", HttpStatus.BAD_REQUEST);
    }
    try {
        teamMemberToPatch.setStatusCurrentCampaign(statusCampaignToUpdate.getStatusUserCampaign());
        teamMemberToPatch = teamMemberService.updateTeamMemberCampaign(teamMemberToPatch);
        TeamMemberDto teamMemberDto = new TeamMemberDto();
        teamMemberDto.setManager(teamMemberToPatch.getManager().getFirstName()+' '+ teamMemberToPatch.getManager().getLastName());
        teamMemberDto.setStatusCurrentCampaign(teamMemberToPatch.getStatusCurrentCampaign());
        teamMemberDto.setStatusVolunteerTrainer(teamMemberToPatch.getStatusVolunteerTrainer());
        teamMemberDto.setStatusLastCampaign(teamMemberToPatch.getStatusLastCampaign());
        return new ResponseEntity<>(teamMemberDto, HttpStatus.OK);
      } catch (IllegalArgumentException exception) {
        return new ResponseEntity<>("Bad Status Campaign", HttpStatus.BAD_REQUEST);
      }
  }
}



