package com.pfe.myteamupskill.services;

import com.pfe.myteamupskill.configuration.SpringSecurityWebAuxTestConfig;
import com.pfe.myteamupskill.models.EStatusUserCampaign;
import com.pfe.myteamupskill.models.TeamMember;
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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = SpringSecurityWebAuxTestConfig.class)
public class TeamMemberServiceTests {

  @Autowired
  private TeamMemberService teamMemberService;

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

  /*@Test
  @DisplayName("Test getTeamMember Not Found")
    void testGetTeamMemberNotFound(){
      Mockito.when(teamMemberRepository.findById(10))
              .thenReturn(Optional.empty());
      Optional<TeamMember> returnedTeamMember = Optional.of(teamMemberService.getTeamMember(10));

      Assertions.asser (IllegalArgumentException.class,"non existant");

    }*/
  @Test
  @DisplayName("Test save TeamMember")
  void testSave(){
    TeamMember teamMember = new TeamMember();
    teamMember.setId(10);
    teamMember.setEmail("first.last@nnn.com");
    teamMember.setPassword("pwd");
    teamMember.setStatusCurrentCampaign(EStatusUserCampaign.OPENED);

    Mockito.when(teamMemberRepository.save(teamMember))
            .thenReturn(teamMember);
    TeamMember returnedTeamMember = teamMemberService.saveUser(teamMember);

    Assertions.assertNotNull(returnedTeamMember,"The save TeamMember should not be null");
  }
  }

