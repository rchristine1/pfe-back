package com.pfe.myteamupskill.services;

import com.pfe.myteamupskill.controllers.TeamMemberCampaignInProgressException;
import com.pfe.myteamupskill.controllers.TeamMemberCampaignSubmissionException;
import com.pfe.myteamupskill.controllers.TeamMemberCampaignValidationException;
import com.pfe.myteamupskill.models.*;
import com.pfe.myteamupskill.repository.TeamMemberRepository;
import com.pfe.myteamupskill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class TeamMemberService {

  @Autowired
  TeamMemberRepository teamMemberRepository;

  @Autowired
  UserSkillService userSkillService;

  @Autowired
  UserRepository userRepository;

  public TeamMember getTeamMember(int id) {
    Optional<TeamMember> teamMemberOptional = teamMemberRepository.findById(id);
    if (teamMemberOptional.isPresent())
      return teamMemberOptional.get();
    else
      throw new IllegalArgumentException("teamMember non existant");
  }

  public List<TeamMember> findByStatusCurrentCampaignAndManagerId(EStatusUserCampaign status, Integer id) {
    Iterable<TeamMember> teamMembers = teamMemberRepository.findByStatusCurrentCampaignAndManagerId(status, id);
    return (List<TeamMember>) teamMembers;
  }

  public List<TeamMember> getTeamMembers() {
    Iterable<TeamMember> teamMembers = teamMemberRepository.findAll();
    return (List<TeamMember>) teamMembers;
  }

  public List<TeamMember> getTeamMembersByManager(Integer id) {
    Iterable<TeamMember> teamMembers = teamMemberRepository.findByManager(id);
    return (List<TeamMember>) teamMembers;
  }

  public TeamMember findOneByLogin(String login) {

    TeamMember teamMember = teamMemberRepository.findOneByLogin(login);
    return teamMember;
  }

  public TeamMember saveUser(TeamMember userEntity) {
    TeamMember user = new TeamMember();
    user.setLogin(userEntity.getLogin());
    user.setPassword(new BCryptPasswordEncoder().encode(userEntity.getPassword()));
    user.setLastName(StringUtils.capitalize(userEntity.getLastName()));
    user.setFirstName(StringUtils.capitalize(userEntity.getFirstName()));
    user.setEmail(StringUtils.capitalize(userEntity.getEmail()));
    teamMemberRepository.save(user);
    return user;
  }

  public TeamMember updateTeamMemberCampaign(TeamMember teamMemberToUpdate) {
    if (teamMemberToUpdate.getStatusCurrentCampaign() == EStatusUserCampaign.SUBMITTED) {
      if (userSkillService.listUserSkills(teamMemberToUpdate).size() !=
              userSkillService.listUserSkillsByTeamMemberByStatus(teamMemberToUpdate, EStatusSkill.MARKED).size())
      {
        throw new TeamMemberCampaignSubmissionException();
      }
    } else if (teamMemberToUpdate.getStatusCurrentCampaign() == EStatusUserCampaign.VALIDATED) {
      if (userSkillService.listUserSkillsByTeamMemberByStatus(teamMemberToUpdate, EStatusSkill.TO_BE_TRAINED).isEmpty()
      && userSkillService.listUserSkillsByTeamMemberByStatus(teamMemberToUpdate, EStatusSkill.MARKED).isEmpty()){
        teamMemberRepository.save(teamMemberToUpdate);
      return teamMemberToUpdate;
    } else {
      throw new TeamMemberCampaignValidationException();
      }
    } else if (teamMemberToUpdate.getStatusCurrentCampaign() == EStatusUserCampaign.IN_PROGRESS) {
      if ((!userSkillService.listUserSkillsByTeamMemberByStatus(teamMemberToUpdate, EStatusSkill.TO_BE_TRAINED).isEmpty())
      && (userSkillService.listUserSkillsByTeamMemberByStatus(teamMemberToUpdate, EStatusSkill.MARKED).isEmpty())) {
        teamMemberRepository.save(teamMemberToUpdate);
        return teamMemberToUpdate;
      } else {
        throw new TeamMemberCampaignInProgressException();
      }
    }
    teamMemberRepository.save(teamMemberToUpdate);
    return teamMemberToUpdate;
  }

  public TeamMember updateTeamMember(TeamMember teamMemberToUpdate) {
    teamMemberRepository.save(teamMemberToUpdate);
    return teamMemberToUpdate;
  }

  }



