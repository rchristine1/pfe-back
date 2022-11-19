package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.models.*;
import com.pfe.myteamupskill.services.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@SecurityRequirement(name = "bearerAuth")
public class UserSkillController {

  @Autowired
  UserService userService;

  @Autowired
  TeamMemberService teamMemberService;

  @Autowired
  UserSkillService userSkillService;

  @Autowired
  DomainService domainService;

  @Autowired
  SkillService skillService;

  @Autowired
  CampaignService campaignService;

  @PreAuthorize("hasRole('ROLE_TEAMMEMBER') or hasRole('ROLE_TEAMLEADER')")
  @GetMapping(value = "/userskills/{teamMemberId}")
  public ResponseEntity listUserSkills(Principal principal,
                                       @PathVariable("teamMemberId") String teamMemberId,
                                       @RequestParam(required = false) String campaignId) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    TeamMember teamMemberSelected = teamMemberService.getTeamMember(Integer.valueOf(teamMemberId));
    if (teamMemberSelected == null) {
      return new ResponseEntity("TeamMember unknown", HttpStatus.BAD_REQUEST);
    }

    if (campaignId != null) {
      Campaign existingCampaign = campaignService.getCampaign(Integer.valueOf(campaignId));
      if (existingCampaign == null) {
        return new ResponseEntity("Campaign not found", HttpStatus.BAD_REQUEST);
      } else {
        List<UserSkill> userSkills = userSkillService.listUserSkills(teamMemberSelected, existingCampaign);
        if (userSkills == null) {
          return new ResponseEntity("UserSkills not found", HttpStatus.BAD_REQUEST);
        }
        List<UserSkillDto> userSkillDTOList = new ArrayList<>();
        for (UserSkill us : userSkills) {
          UserSkillDto userSkillDTO = new UserSkillDto();
          userSkillDTO.setUserSkillId(us.getId());
          userSkillDTO.setUserId(us.getTeamMember().getId());
          userSkillDTO.setLabel(us.getSkill().getLabel());
          userSkillDTO.setLabelDomain(us.getSkill().getDomain().getLabel());
          userSkillDTO.setLabelCampaign(us.getCampaign().getLabel());
          userSkillDTO.setMark(us.getMark());
          userSkillDTO.setLastWriterId(us.getLastWriterId());
          userSkillDTO.setStatusSkill(us.getStatusSkill());
          userSkillDTOList.add(userSkillDTO);
        }
        return new ResponseEntity(userSkillDTOList, HttpStatus.OK);
      }
    } else {
      List<UserSkill> userSkills = userSkillService.listUserSkills(teamMemberSelected);
      if (userSkills == null) {
        return new ResponseEntity("UserSkills not found", HttpStatus.BAD_REQUEST);
      }
      List<UserSkillDto> userSkillDTOList = new ArrayList<>();
      for (UserSkill us : userSkills) {
        UserSkillDto userSkillDTO = new UserSkillDto();
        userSkillDTO.setUserSkillId(us.getId());
        userSkillDTO.setUserId(us.getTeamMember().getId());
        userSkillDTO.setLabel(us.getSkill().getLabel());
        userSkillDTO.setLabelDomain(us.getSkill().getDomain().getLabel());
        userSkillDTO.setLabelCampaign(us.getCampaign().getLabel());
        userSkillDTO.setMark(us.getMark());
        userSkillDTO.setLastWriterId(us.getLastWriterId());
        userSkillDTO.setStatusSkill(us.getStatusSkill());
        userSkillDTOList.add(userSkillDTO);
      }
      return new ResponseEntity(userSkillDTOList, HttpStatus.OK);
    }
  }

  @PreAuthorize("hasRole('ROLE_TEAMMEMBER') or hasRole('ROLE_TEAMLEADER')")
  @PostMapping("/userskills/{campaignId}")
  public ResponseEntity add(Principal principal, @PathVariable("campaignId") String campaignId) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    Campaign existingCampaign = campaignService.getCampaign(Integer.valueOf(campaignId));
    if (existingCampaign == null) {
      return new ResponseEntity("Campaign unknown", HttpStatus.BAD_REQUEST);
    }

    User existingUser = userService.getUser(userConnectedId);
    if (existingUser == null) {
      return new ResponseEntity("User unknown", HttpStatus.BAD_REQUEST);
    }

    List<Domain> domains = domainService.getDomains();
    HashMap<String, UserSkill> mapUserSkills = new HashMap<String, UserSkill>();
    for (Domain d : domains) {
      List<Skill> domainSkills = skillService.getDomainSkills(d.getLabel());
      for (Skill s : domainSkills) {

          UserSkill userSkill = userSkillService.saveUserSkill(s, (TeamMember) existingUser, existingCampaign, userConnectedId);
          mapUserSkills.put(d.getLabel(), userSkill);
      }
    }
     return new ResponseEntity<>(mapUserSkills, HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ROLE_TEAMMEMBER') or hasRole('ROLE_TEAMLEADER')")
  @PatchMapping(value = "/userskills/{userSkillId}/mark")
  public ResponseEntity updateMark(Principal principal,
                                   @PathVariable("userSkillId") String userSkillId,
                                   @Valid @RequestBody UserSkillMarkDto markToUpdate) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    User existingUser = userService.getUser(userConnectedId);
    if (existingUser == null) {
      return new ResponseEntity("User unknown", HttpStatus.NOT_FOUND);
    }
    UserSkill userSkillToUpdate = userSkillService.getUserSkill(Integer.valueOf(userSkillId));
    if (userSkillToUpdate == null) {
      return new ResponseEntity("UserSkill not existing", HttpStatus.NOT_FOUND);
    }
    userSkillService.updateUserSkillMarkAndStatus(
                userSkillToUpdate,markToUpdate.getMark(),
                userSkillToUpdate.getMark(), existingUser);
    UserSkillDto userSkillDTO = new UserSkillDto();
    userSkillDTO.setUserSkillId(userSkillToUpdate.getId());
    userSkillDTO.setUserId(userSkillToUpdate.getTeamMember().getId());
    userSkillDTO.setLabel(userSkillToUpdate.getSkill().getLabel());
    userSkillDTO.setLabelDomain(userSkillToUpdate.getSkill().getDomain().getLabel());
    userSkillDTO.setLabelCampaign(userSkillToUpdate.getCampaign().getLabel());
    userSkillDTO.setMark(userSkillToUpdate.getMark());
    userSkillDTO.setLastWriterId(userSkillToUpdate.getLastWriterId());
    userSkillDTO.setStatusSkill(userSkillToUpdate.getStatusSkill());
    return new ResponseEntity<>(userSkillDTO, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_TEAMLEADER')")
  @PatchMapping(value = "/userskills/{userSkillId}/statusSkill")
  public ResponseEntity updateStatusSkill(Principal principal,
                                   @PathVariable("userSkillId") String userSkillId,
                                   @Valid @RequestBody UserSkillStatusDto statusToUpdate) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    User existingUser = userService.getUser(userConnectedId);

    if (existingUser == null) {
      return new ResponseEntity("User unknown", HttpStatus.NOT_FOUND);
    }

    UserSkill userSkillToPatch = userSkillService.getUserSkill(Integer.valueOf(userSkillId));
    if (userSkillToPatch == null) {
      return new ResponseEntity("UserSkill not existing", HttpStatus.NOT_FOUND);
    }
    try {
      userSkillToPatch.setStatusSkill(EStatusSkill.valueOf(statusToUpdate.getStatusSkill()));
      userSkillToPatch = userSkillService.updateUserSkill(userSkillToPatch);
      UserSkillDto userSkillDTO = new UserSkillDto();
      userSkillDTO.setUserSkillId(userSkillToPatch.getId());
      userSkillDTO.setUserId(userSkillToPatch.getTeamMember().getId());
      userSkillDTO.setLabel(userSkillToPatch.getSkill().getLabel());
      userSkillDTO.setLabelDomain(userSkillToPatch.getSkill().getDomain().getLabel());
      userSkillDTO.setLabelCampaign(userSkillToPatch.getCampaign().getLabel());
      userSkillDTO.setMark(userSkillToPatch.getMark());
      userSkillDTO.setLastWriterId(userSkillToPatch.getLastWriterId());
      userSkillDTO.setStatusSkill(userSkillToPatch.getStatusSkill());
      return new ResponseEntity<>(userSkillDTO, HttpStatus.OK);
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity<>("Status should be VALIDATED or TO_BE_TRAINED", HttpStatus.BAD_REQUEST);
    }
  }
}


