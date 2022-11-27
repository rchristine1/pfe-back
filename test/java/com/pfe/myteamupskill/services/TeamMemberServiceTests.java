package com.pfe.myteamupskill.services;

import com.pfe.myteamupskill.configuration.SpringSecurityWebAuxTestConfig;
import com.pfe.myteamupskill.controllers.dto.TeamMemberCampaignDto;
import com.pfe.myteamupskill.models.*;
import com.pfe.myteamupskill.repository.TeamMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = SpringSecurityWebAuxTestConfig.class)
@ActiveProfiles("test")
public class TeamMemberServiceTests {

  @Autowired
  private TeamMemberService teamMemberService;

  @MockBean
  private UserSkillService userSkillService;

  @MockBean
  private TeamMemberRepository teamMemberRepository;

  @Test
  @DisplayName("Test getTeamMember Success")
  void testGetTeamMember(){
    TeamMember teamMember = new TeamMember();
    teamMember.setId(10);
    teamMember.setEmail("first.last@nnn.com");
    teamMember.setStatusCurrentCampaign(EStatusUserCampaign.OPENED);

    Mockito.when(teamMemberRepository.findById(10))
            .thenReturn(Optional.of(teamMember));

    Optional<TeamMember> returnedTeamMember = Optional.of(teamMemberService.getTeamMember(10));

    Assertions.assertTrue(returnedTeamMember.isPresent(),"TeamMember was not found");
    Assertions.assertSame(returnedTeamMember.get(),teamMember,"The TeamMember returned was not the same as the mock");
  }
  @Test
  @DisplayName("Test FindOneByLogin")
  void testFindOneByLogin(){
    TeamMember teamMember = new TeamMember();
    teamMember.setId(10);
    teamMember.setManager(new Manager(1,new Team(1,"DIV","DEPT","TEAM")));
    teamMember.setLogin("first.last");
    teamMember.setStatusCurrentCampaign(EStatusUserCampaign.OPENED);

    Mockito.when(teamMemberRepository.findOneByLogin("first.last"))
            .thenReturn(teamMember);

    TeamMember returnedTeamMember = teamMemberService.findOneByLogin("first.last");

    Assertions.assertTrue(returnedTeamMember.getLogin().equals("first.last"),"TeamMember was not found");
    Assertions.assertSame(returnedTeamMember,teamMember,"The TeamMember returned was not the same as the mock");
  }


  @Test
  @DisplayName("Test save TeamMember")
  void testSaveUser(){
    TeamMember teamMember = new TeamMember();
    teamMember.setId(10);
    teamMember.setLogin("first.last");
    teamMember.setEmail("first.last@nnn.com");
    teamMember.setLastName("last");
    teamMember.setFirstName("first");
    teamMember.setPassword("pwd");
    teamMember.setStatusCurrentCampaign(EStatusUserCampaign.OPENED);

    Mockito.when(teamMemberRepository.save(teamMember))
            .thenReturn(teamMember);
    TeamMember returnedTeamMember = teamMemberService.saveUser(teamMember);

    Assertions.assertNotNull(returnedTeamMember,"The save TeamMember should not be null");
    Assertions.assertTrue(returnedTeamMember.getLogin().equals("first.last"),"The save TeamMember login should be first.last");
  }

  /*@Test
  @DisplayName("Test updateTeamMemberCampaign TeamMember")
  void testUpdateTeamMemberCampaign(){
    TeamMember teamMember = new TeamMember();
    teamMember.setId(10);
    teamMember.setManager(new Manager(1,new Team(1,"DIV","DEPT","TEAM")));
    teamMember.setLogin("first.last");
    teamMember.setStatusCurrentCampaign(EStatusUserCampaign.OPENED);

    TeamMemberCampaignDto teamMemberCampaignDto = new TeamMemberCampaignDto();
    teamMemberCampaignDto.setStatusUserCampaign(EStatusUserCampaign.SUBMITTED);

    UserSkill userSkill1 = new UserSkill(1,teamMember,new Skill("Label1",new Domain("Domain1")),new Campaign("LabelCampaign"),1,10,EStatusSkill.MARKED);
    List<UserSkill> userSkillsList = Arrays.asList(userSkill1);

    Mockito.when(teamMemberRepository.save(teamMember))
            .thenReturn(teamMember);

    Mockito.when(userSkillService.listUserSkills(teamMember))
            .thenReturn(userSkillsList);

    Mockito.when(teamMemberService.updateTeamMember(teamMember))
            .thenReturn(teamMember);

    Mockito.when(teamMemberService.getTeamMember(10))
            .thenReturn(teamMember);

    TeamMember returnedTeamMember = teamMemberService.updateTeamMemberCampaign("10",teamMemberCampaignDto);

    Assertions.assertTrue(returnedTeamMember.getStatusCurrentCampaign().equals("SUBMITTED"),"The update TeamMember StatusCurrentCampaign should not be SUBMITTED");

  }*/
  }

