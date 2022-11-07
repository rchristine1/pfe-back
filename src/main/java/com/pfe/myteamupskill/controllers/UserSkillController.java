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
import java.util.stream.Stream;

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
  public ResponseEntity listUserSkills(Principal principal,@PathVariable("teamMemberId") String teamMemberId) {
  //public ResponseEntity listUserSkills(Principal principal) {
    //Integer userConnectedId = userService.getUserConnectedId(principal);
    Integer userConnectedId = Integer.valueOf(teamMemberId);
    TeamMember teamMemberSelected = teamMemberService.getTeamMember(userConnectedId);
    List<UserSkill> userSkills = userSkillService.listUserSkills(teamMemberSelected);
    List<UserSkillDTO> userSkillDTOList = new ArrayList<>();
    for (UserSkill us: userSkills) {
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
  public ResponseEntity add(Principal principal,@PathVariable("campaignId") String campaignId) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    User existingUser = userService.getUser(userConnectedId);
    if (existingUser == null) {
      return new ResponseEntity("User unknown", HttpStatus.BAD_REQUEST);
    }
    Campaign campaign = campaignService.getCampaign(Integer.valueOf(campaignId));
    List<Domain> domains = domainService.getDomains();
    HashMap<String, UserSkill> mapUserSkills = new HashMap<String, UserSkill>();
    for (Domain d:domains){
      List<Skill> domainSkills = skillService.getDomainSkills(d.getLabel());
      for (Skill s:domainSkills) {
        UserSkill userSkill = userSkillService.saveUserSkill(s,(TeamMember) existingUser,campaign,userConnectedId);
        mapUserSkills.put(d.getLabel(), userSkill);
      }
    }
    TeamMember teamMemberCampaign = teamMemberService.updateTeamMemberCampaign((TeamMember)existingUser,EStatusUserCampaign.INITIALIZED);
    System.out.println("userskills "+teamMemberCampaign.getStatusCurrentCampaign());
    return new ResponseEntity<>(mapUserSkills, HttpStatus.CREATED);
    }
  @PutMapping(value = "/userskills/{userSkillId}")
  public ResponseEntity update(Principal principal,@PathVariable("userSkillId") String userSkillId,@Valid @RequestBody UserSkill userSkill,@RequestParam(required = false) EStatusSkill status) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    User existingUser = userService.getUser(userConnectedId);
    if (existingUser == null) {
      return new ResponseEntity("User unknown", HttpStatus.NOT_FOUND);
    }
    UserSkill userSkillToUpdate = userSkillService.getUserSkill(Integer.valueOf(userSkillId));
    if (userSkillToUpdate == null) {
      return new ResponseEntity("UserSkill not existing", HttpStatus.NOT_FOUND);
    }

    //Case marked 0
    if (status == EStatusSkill.MARKED && userSkill.getMark() == userSkillToUpdate.getMark()){
      userSkillService.updateUserSkill(userSkillToUpdate,userConnectedId,status);
    } else if (status != EStatusSkill.MARKED && userSkill.getMark() == userSkillToUpdate.getMark()){
      //case validated && tobetrained
      userSkillService.updateUserSkill(userSkillToUpdate,status);
    } else {
      //case revised && tobetrained && marked
      userSkillService.updateUserSkill(userSkillToUpdate,userConnectedId,userSkill.getMark(),status);
    }

    return new ResponseEntity<>(userSkillToUpdate, HttpStatus.OK);
  }

  @PatchMapping(value = "/userskills/{userSkillId}/mark")
  public ResponseEntity updateMark(Principal principal,@PathVariable("userSkillId") String userSkillId,@Valid @RequestBody UserSkillMarkDTO markToUpdate) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    User existingUser = userService.getUser(userConnectedId);
    if (existingUser == null) {
      return new ResponseEntity("User unknown", HttpStatus.NOT_FOUND);
    }
    UserSkill userSkillToUpdate = userSkillService.getUserSkill(Integer.valueOf(userSkillId));
    if (userSkillToUpdate == null) {
      return new ResponseEntity("UserSkill not existing", HttpStatus.NOT_FOUND);
    }

    userSkillToUpdate.setMark(markToUpdate.getMark());
    // Verifier si la note est = et comprise entre 0 et 2
    //Renvoie bad request
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
  }


}
