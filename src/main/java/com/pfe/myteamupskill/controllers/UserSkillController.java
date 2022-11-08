package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.models.*;
import com.pfe.myteamupskill.services.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


  @GetMapping(value = "/userskills/{teamMemberId}")
  //@GetMapping(value = "/userskills")
  public ResponseEntity listUserSkills(Principal principal, @PathVariable("teamMemberId") String teamMemberId) {
    //public ResponseEntity listUserSkills(Principal principal) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    Integer teamMemberIdValue = Integer.valueOf(teamMemberId);
    TeamMember teamMemberSelected = teamMemberService.getTeamMember(teamMemberIdValue);
    List<UserSkill> userSkills = userSkillService.listUserSkills(teamMemberSelected);
    List<UserSkillDTO> userSkillDTOList = new ArrayList<>();
    for (UserSkill us : userSkills) {
      UserSkillDTO userSkillDTO = new UserSkillDTO();
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

  @PostMapping("/userskills/{campaignId}")
  public ResponseEntity add(Principal principal, @PathVariable("campaignId") String campaignId) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    User existingUser = userService.getUser(userConnectedId);
    if (existingUser == null) {
      return new ResponseEntity("User unknown", HttpStatus.BAD_REQUEST);
    }
    Campaign campaign = campaignService.getCampaign(Integer.valueOf(campaignId));
    List<Domain> domains = domainService.getDomains();
    HashMap<String, UserSkill> mapUserSkills = new HashMap<String, UserSkill>();
    for (Domain d : domains) {
      List<Skill> domainSkills = skillService.getDomainSkills(d.getLabel());
      for (Skill s : domainSkills) {
        UserSkill userSkill = userSkillService.saveUserSkill(s, (TeamMember) existingUser, campaign, userConnectedId);
        mapUserSkills.put(d.getLabel(), userSkill);
      }
    }
    /*TeamMember teamMemberCampaign = teamMemberService.updateTeamMemberCampaign((TeamMember) existingUser, EStatusUserCampaign.INITIALIZED);
    System.out.println("userskills " + teamMemberCampaign.getStatusCurrentCampaign());*/
    return new ResponseEntity<>(mapUserSkills, HttpStatus.CREATED);
  }

  @PatchMapping(value = "/userskills/{userSkillId}/mark")
  public ResponseEntity updateMark(Principal principal,
                                   @PathVariable("userSkillId") String userSkillId,
                                   @Valid @RequestBody UserSkillMarkDTO markToUpdate) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    User existingUser = userService.getUser(userConnectedId);
    if (existingUser == null) {
      return new ResponseEntity("User unknown", HttpStatus.NOT_FOUND);
    }
    UserSkill userSkillToUpdate = userSkillService.getUserSkill(Integer.valueOf(userSkillId));
    if (userSkillToUpdate == null) {
      return new ResponseEntity("UserSkill not existing", HttpStatus.NOT_FOUND);
    }

    //if (userSkillToUpdate.getTeamMember().getStatusCurrentCampaign() == EStatusUserCampaign.INITIALIZED
     // if (markToUpdate.getMark() != userSkillToUpdate.getMark() ){
       try {
          userSkillToUpdate.setMark(markToUpdate.getMark());
          userSkillToUpdate.setStatusSkill(EStatusSkill.MARKED);
          userSkillToUpdate = userSkillService.updateUserSkill(userSkillToUpdate);
          UserSkillDTO userSkillDTO = new UserSkillDTO();
          userSkillDTO.setUserSkillId(userSkillToUpdate.getId());
          userSkillDTO.setUserId(userSkillToUpdate.getTeamMember().getId());
          userSkillDTO.setLabel(userSkillToUpdate.getSkill().getLabel());
          userSkillDTO.setLabelDomain(userSkillToUpdate.getSkill().getDomain().getLabel());
          userSkillDTO.setLabelCampaign(userSkillToUpdate.getCampaign().getLabel());
          userSkillDTO.setMark(userSkillToUpdate.getMark());
          userSkillDTO.setLastWriterId(userSkillToUpdate.getLastWriterId());
          userSkillDTO.setStatusSkill(userSkillToUpdate.getStatusSkill());
          return new ResponseEntity<>(userSkillDTO, HttpStatus.OK);
       } catch (IllegalArgumentException exception) {
          return new ResponseEntity<>("Mark should be 0,1 or 2", HttpStatus.BAD_REQUEST);
        }
     /* } else {System.out.println("Pas de mise à jour car la note est la même");}
     } else {
        return new ResponseEntity<>("Mark rules not respected", HttpStatus.BAD_REQUEST);
     }
    return new ResponseEntity("StatusCampaign not good", HttpStatus.BAD_REQUEST);*/
   }

  @PatchMapping(value = "/userskills/{userSkillId}/statusSkill")
  public ResponseEntity updateStatusSkill(Principal principal,
                                   @PathVariable("userSkillId") String userSkillId,
                                   @Valid @RequestBody UserSkillStatusDTO statusToUpdate) {
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
      UserSkillDTO userSkillDTO = new UserSkillDTO();
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


