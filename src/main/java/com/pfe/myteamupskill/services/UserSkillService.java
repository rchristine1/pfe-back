package com.pfe.myteamupskill.services;

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


  public UserSkill saveUserSkill (Skill skillEntity, TeamMember teamMemberEntity, Campaign campaignEntity, Integer userWriterId) {
    UserSkill userSkills = new UserSkill();
    userSkills.setSkill(skillEntity);
    userSkills.setTeamMember(teamMemberEntity);
    userSkills.setCampaign(campaignEntity);
    userSkills.setCampaign(campaignEntity);
    userSkills.setLastWriterId(userWriterId);
    userSkills.setStatusSkill(EStatusSkill.INITIALIZED);
    userSkillsRepository.save(userSkills);
    return userSkills;
  }

  public UserSkill updateUserSkill (UserSkill skillEntity, Integer userWriterId,Integer mark) {
    skillEntity.setMark(mark);
    skillEntity.setLastWriterId(userWriterId);
    skillEntity.setStatusSkill(EStatusSkill.MARKED);
    userSkillsRepository.save(skillEntity);
    return skillEntity;
  }


  public UserSkill updateUserSkill (UserSkill skillEntity, Integer userWriterId,Integer mark,EStatusSkill STATUS) {
    skillEntity.setMark(mark);
    skillEntity.setLastWriterId(userWriterId);
    skillEntity.setStatusSkill(STATUS);
    userSkillsRepository.save(skillEntity);
    return skillEntity;
  }

  public UserSkill updateUserSkill (UserSkill skillEntity,Integer userWriterId, EStatusSkill STATUS) {
    skillEntity.setLastWriterId(userWriterId);
    skillEntity.setStatusSkill(STATUS);
    userSkillsRepository.save(skillEntity);
    return skillEntity;
  }

  public UserSkill updateUserSkill (UserSkill skillEntity,EStatusSkill STATUS) {
    skillEntity.setStatusSkill(STATUS);
    userSkillsRepository.save(skillEntity);
    return skillEntity;
  }



  public UserSkill getUserSkill(int id) {
    Optional<UserSkill> userSkillsOptional=userSkillsRepository.findById(id);
    if (userSkillsOptional.isPresent())
      return userSkillsOptional.get();
    else
      throw new IllegalArgumentException("userSkillsOptional not existing");
  }

  public UserSkill updateUserSkill(UserSkill userSkillToUpdate) {
    return userSkillsRepository.save(userSkillToUpdate);
  }
}
