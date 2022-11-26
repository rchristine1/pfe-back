package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.controllers.dto.TeamMemberCampaignDto;
import com.pfe.myteamupskill.controllers.dto.TeamMemberDto;
import com.pfe.myteamupskill.models.*;
import com.pfe.myteamupskill.security.jwt.JwtController;
import com.pfe.myteamupskill.security.jwt.JwtUtils;
import com.pfe.myteamupskill.services.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
  public ResponseEntity getTeamMember(Principal principal, @PathVariable("teamMemberId") String teamMemberId) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    Integer teamMemberIdValue = Integer.valueOf(teamMemberId);
    TeamMember teamMemberSelected = teamMemberService.getTeamMember(teamMemberIdValue);
    TeamMemberDto teamMemberDTO = new TeamMemberDto();
    teamMemberDTO.setFullNameManager(teamMemberSelected.getManager().getFirstName()+' '+ teamMemberSelected.getManager().getLastName());
    teamMemberDTO.setStatusCurrentCampaign(teamMemberSelected.getStatusCurrentCampaign());
    teamMemberDTO.setStatusVolunteerTrainer(teamMemberSelected.getStatusVolunteerTrainer());
    teamMemberDTO.setStatusLastCampaign(teamMemberSelected.getStatusLastCampaign());
    teamMemberDTO.setFirstname(teamMemberSelected.getFirstName());
    teamMemberDTO.setLastname(teamMemberSelected.getLastName());

    return new ResponseEntity(teamMemberDTO, HttpStatus.OK);
  }


  @PreAuthorize("hasRole('ROLE_TEAMLEADER')")
  @GetMapping(value = "/teammembers")
  public ResponseEntity getTeamMembers(Principal principal,
                                       @RequestParam(required = false) String status,
                                       @RequestParam(required = true) String managerId){
    Integer userConnectedId = userService.getUserConnectedId(principal);
    List<TeamMember> teamMemberList = teamMemberService.getTeamMembers(Integer.valueOf(managerId),status);

    List<TeamMemberDto> teamMemberDTOList = new ArrayList<>();
    for (TeamMember tm :teamMemberList) {
      TeamMemberDto teamMemberDTO = new TeamMemberDto();
      teamMemberDTO.setFullNameManager(tm.getManager().getFirstName()+' '+ tm.getManager().getLastName());
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

    TeamMember user = teamMemberService.createUser(userEntity);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Location", "/teammembers" + user.getId());

    return new ResponseEntity<>(httpHeaders,HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ROLE_TEAMLEADER')")
  @PutMapping("/teammembers/{teamMemberId}")
  public ResponseEntity updateTeamMember(@PathVariable("teamMemberId") String teamMemberId,
                                         @Valid @RequestBody TeamMemberDto teamMemberDto) {

    TeamMember teamMemberToPatch = teamMemberService.getTeamMember(Integer.valueOf(teamMemberId));
    if (teamMemberToPatch == null) {
      return new ResponseEntity("User not found", HttpStatus.BAD_REQUEST);
    }

    teamMemberToPatch.setManager(managerService.findById(teamMemberDto.getManagerId()));
    teamMemberToPatch.setStatusCurrentCampaign(teamMemberDto.getStatusCurrentCampaign());
    teamMemberToPatch.setStatusVolunteerTrainer(teamMemberDto.getStatusVolunteerTrainer());
    teamMemberToPatch.setStatusLastCampaign(teamMemberDto.getStatusLastCampaign());
    teamMemberToPatch.setEmail(teamMemberDto.getEmail());
    teamMemberToPatch.setFirstName(teamMemberDto.getFirstname());
    teamMemberToPatch.setLastName(teamMemberDto.getLastname());
    teamMemberToPatch.setPicture(teamMemberDto.getPicture());
    teamMemberToPatch = teamMemberService.updateTeamMember(teamMemberToPatch);
    TeamMemberDto teamMemberDtoInput = new TeamMemberDto();
    teamMemberDtoInput.setManagerId(teamMemberToPatch.getManager().getId());
    teamMemberDtoInput.setEmail(teamMemberToPatch.getEmail());
    teamMemberDtoInput.setStatusCurrentCampaign(teamMemberToPatch.getStatusCurrentCampaign());
    teamMemberDtoInput.setStatusVolunteerTrainer(teamMemberToPatch.getStatusVolunteerTrainer());
    teamMemberDtoInput.setStatusLastCampaign(teamMemberToPatch.getStatusLastCampaign());
    teamMemberDtoInput.setFirstname(teamMemberToPatch.getFirstName());
    teamMemberDtoInput.setLastname(teamMemberToPatch.getLastName());
    teamMemberDtoInput.setPicture(teamMemberToPatch.getPicture());
    return new ResponseEntity<>(teamMemberDtoInput, HttpStatus.OK);
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
    try {
        TeamMember teamMemberToPatch = teamMemberService.updateTeamMemberCampaign(teamMemberId,statusCampaignToUpdate);
        TeamMemberDto teamMemberDto = new TeamMemberDto();
        teamMemberDto.setFullNameManager(teamMemberToPatch.getManager().getFirstName()+' '+ teamMemberToPatch.getManager().getLastName());
        teamMemberDto.setStatusCurrentCampaign(teamMemberToPatch.getStatusCurrentCampaign());
        teamMemberDto.setStatusVolunteerTrainer(teamMemberToPatch.getStatusVolunteerTrainer());
        teamMemberDto.setStatusLastCampaign(teamMemberToPatch.getStatusLastCampaign());
        return new ResponseEntity<>(teamMemberDto, HttpStatus.OK);
      } catch (IllegalArgumentException exception) {
        return new ResponseEntity<>("Bad Status Campaign", HttpStatus.BAD_REQUEST);
      }
  }
}



