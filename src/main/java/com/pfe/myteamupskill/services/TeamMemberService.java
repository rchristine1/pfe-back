package com.pfe.myteamupskill.services;

import com.pfe.myteamupskill.models.*;
import com.pfe.myteamupskill.repository.TeamMemberRepository;
import com.pfe.myteamupskill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class TeamMemberService {

  @Autowired
  TeamMemberRepository teamMemberRepository;

  @Autowired
  UserRepository userRepository;

  public TeamMember getTeamMember(int id) {
    Optional<TeamMember> teamMemberOptional=teamMemberRepository.findById(id);
    if (teamMemberOptional.isPresent())
      return teamMemberOptional.get();
    else
      throw new IllegalArgumentException("teamMember non existant");
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

  public TeamMember updateTeamMemberCampaign (TeamMember teamMemberToUpdate,EStatusUserCampaign STATUS) {
    teamMemberToUpdate.setStatusCurrentCampaign(STATUS);
    teamMemberRepository.save(teamMemberToUpdate);
    return teamMemberToUpdate;
  }
}
