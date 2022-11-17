package com.pfe.myteamupskill.services;

import com.pfe.myteamupskill.controllers.UserSkillAlreadyExistException;
import com.pfe.myteamupskill.controllers.UserSkillMarkNotChangedException;
import com.pfe.myteamupskill.controllers.UserSkillMarkRulesException;
import com.pfe.myteamupskill.controllers.UserSkillNotFoundException;
import com.pfe.myteamupskill.models.*;
import com.pfe.myteamupskill.repository.UserSkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSkillService {

  @Autowired
  UserSkillsRepository userSkillsRepository;

  public List<UserSkill> listUserSkills(TeamMember teamMember){
    Iterable<UserSkill> userSkills= userSkillsRepository.findByTeamMember(teamMember);
    return (List<UserSkill>) userSkills;
  }

  public List<UserSkill> listUserSkills(TeamMember teamMember,Campaign campaign){
    Iterable<UserSkill> userSkills= userSkillsRepository.findByTeamMemberAndCampaign(teamMember,campaign);
    return (List<UserSkill>) userSkills;
  }

  public List<UserSkill> listUserSkillsByTeamMemberByStatus(TeamMember teamMember,EStatusSkill statusSkill){
    Iterable<UserSkill> userSkillsMarked = userSkillsRepository.findByTeamMemberAndStatusSkill(teamMember,statusSkill);
    return (List<UserSkill>) userSkillsMarked;
  }

  public UserSkill saveUserSkill (Skill skillEntity,
                                  TeamMember teamMemberEntity,
                                  Campaign campaignEntity,
                                  Integer userWriterId) {
    UserSkill userSkill = new UserSkill();
    userSkill.setSkill(skillEntity);
    userSkill.setTeamMember(teamMemberEntity);
    userSkill.setCampaign(campaignEntity);
    userSkill.setLastWriterId(userWriterId);
    userSkill.setStatusSkill(EStatusSkill.INITIALIZED);
    userSkill.setMark(-1);
    if ( userSkillsRepository.findBySkillAndTeamMemberAndCampaign(skillEntity,teamMemberEntity,campaignEntity) == null){
    userSkillsRepository.save(userSkill);
    return userSkill;
  }
    else { throw new UserSkillAlreadyExistException();}
  }

  public UserSkill getUserSkill(int id) {
    Optional<UserSkill> userSkillsOptional= userSkillsRepository.findById(id);
    if (userSkillsOptional.isPresent())
      return userSkillsOptional.get();
    else
      throw new UserSkillNotFoundException();
  }

  public UserSkill updateUserSkill(UserSkill userSkillToUpdate) {
    if ( userSkillToUpdate.getMark() >=0 || userSkillToUpdate.getMark() <= 2) {
      return userSkillsRepository.save(userSkillToUpdate);
    } else {
      throw new UserSkillMarkRulesException();
    }
  }

  public UserSkill updateUserSkillMarkAndStatus(UserSkill userSkillToUpdate,
                                                Integer markToUpdate,Integer markCurrent,
                                                User existingUser) {
    if (markToUpdate != markCurrent){
      userSkillToUpdate.setMark(markToUpdate);
      if (existingUser instanceof TeamMember){
        userSkillToUpdate.setStatusSkill(EStatusSkill.MARKED);}
      else {
        userSkillToUpdate.setStatusSkill(EStatusSkill.REVISED);
        userSkillToUpdate.setLastWriterId(existingUser.getId());
      }
      return updateUserSkill(userSkillToUpdate);
    }
    else { throw new UserSkillMarkNotChangedException();
    }
  }
}
