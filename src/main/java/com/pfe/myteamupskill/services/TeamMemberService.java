package com.pfe.myteamupskill.services;

import com.pfe.myteamupskill.controllers.dto.TeamMemberCampaignDto;
import com.pfe.myteamupskill.exceptions.*;
import com.pfe.myteamupskill.models.*;
import com.pfe.myteamupskill.repository.TeamMemberRepository;
import com.pfe.myteamupskill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @Autowired
  ManagerService managerService;

  public TeamMember getTeamMember(int id) {
    Optional<TeamMember> teamMemberOptional = teamMemberRepository.findById(id);
    if (teamMemberOptional.isPresent())
      return teamMemberOptional.get();
    else
      throw new TeamMemberNotFoundException();
  }

  public List<TeamMember> getTeamMembers(Integer id,String status) {
    if (id == null) {
      Iterable<TeamMember> teamMembers = teamMemberRepository.findAll();
      return (List<TeamMember>) teamMembers;
    } else {
      Manager existingManager = managerService.findById(id);
      if (existingManager == null) {
        throw new ManagerNotFoundException();
      } else if (status != null) {
        Iterable<TeamMember> teamMembers = teamMemberRepository
                .findByStatusCurrentCampaignAndManagerId(EStatusUserCampaign.valueOf(status), id);
        return (List<TeamMember>) teamMembers;
      } else {
        Iterable<TeamMember> teamMembers = teamMemberRepository.findByManager(id);
        return (List<TeamMember>) teamMembers;
      }

    }
  }

  public TeamMember findOneByLogin(String login) {

    TeamMember teamMember = teamMemberRepository.findOneByLogin(login);
    return teamMember;
  }

  public TeamMember createUser(TeamMember teamMember){
    TeamMember existingUser = findOneByLogin(teamMember.getLogin());
    if (existingUser != null) {
      throw new TeamMemberAlreadyExistingException();
    }
    return saveUser(teamMember);
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

  public TeamMember updateTeamMemberCampaign(String teamMemberId,TeamMemberCampaignDto statusCampaignToUpdate) {
    TeamMember teamMemberToPatch = getTeamMember(Integer.valueOf(teamMemberId));
    if (teamMemberToPatch == null) {
      throw new TeamMemberNotFoundException ();
    }
    if (statusCampaignToUpdate.getStatusUserCampaign() == null) {
      throw new TeamMemberUserCampaignBadStatusException ();
    }
    teamMemberToPatch.setStatusCurrentCampaign(statusCampaignToUpdate.getStatusUserCampaign());
    if (teamMemberToPatch.getStatusCurrentCampaign() == EStatusUserCampaign.SUBMITTED) {
      if (userSkillService.listUserSkills(teamMemberToPatch).size() !=
              userSkillService.listUserSkillsByTeamMemberByStatus(teamMemberToPatch, EStatusSkill.MARKED).size())
      {
        throw new TeamMemberCampaignSubmissionException();
      }
    } else if (teamMemberToPatch.getStatusCurrentCampaign() == EStatusUserCampaign.VALIDATED) {
      if (userSkillService.listUserSkillsByTeamMemberByStatus(teamMemberToPatch, EStatusSkill.TO_BE_TRAINED).isEmpty()
      && userSkillService.listUserSkillsByTeamMemberByStatus(teamMemberToPatch, EStatusSkill.MARKED).isEmpty()){
        updateTeamMember(teamMemberToPatch);
      return teamMemberToPatch;
    } else {
      throw new TeamMemberCampaignValidationException();
      }
    } else if (teamMemberToPatch.getStatusCurrentCampaign() == EStatusUserCampaign.IN_PROGRESS) {
      if ((!userSkillService.listUserSkillsByTeamMemberByStatus(teamMemberToPatch, EStatusSkill.TO_BE_TRAINED).isEmpty())
      && (userSkillService.listUserSkillsByTeamMemberByStatus(teamMemberToPatch, EStatusSkill.MARKED).isEmpty())) {
        updateTeamMember(teamMemberToPatch);
        return teamMemberToPatch;
      } else {
        throw new TeamMemberCampaignInProgressException();
      }
    }
    updateTeamMember(teamMemberToPatch);
    return teamMemberToPatch;
  }

  public TeamMember updateTeamMember(TeamMember teamMemberToUpdate) {
    teamMemberRepository.save(teamMemberToUpdate);
    return teamMemberToUpdate;
  }

  }



