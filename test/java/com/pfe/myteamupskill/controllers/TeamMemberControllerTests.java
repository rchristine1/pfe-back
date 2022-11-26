package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.configuration.SpringSecurityWebAuxTestConfig;
import com.pfe.myteamupskill.controllers.dto.TeamMemberCampaignDto;
import com.pfe.myteamupskill.models.EStatusUserCampaign;
import com.pfe.myteamupskill.models.TeamMember;
import com.pfe.myteamupskill.services.TeamMemberService;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = SpringSecurityWebAuxTestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TeamMemberControllerTests {

  public MockMvc mockMvc;

  @MockBean
  public TeamMemberController teamMemberController;

  @MockBean
  public TeamMemberService teamMemberService;

  private ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(teamMemberController
    ).build();
  }

  @Test
  @WithUserDetails("teammemberfirst.teammemberlast")
  void testUpdateTeamMemberCampaign() throws Exception{
   TeamMember teamMember = new TeamMember();
    teamMember.setId(10);
    teamMember.setEmail("first.last@nnn.com");
    teamMember.setStatusCurrentCampaign(EStatusUserCampaign.OPENED);

    TeamMemberCampaignDto teamMemberCampaignDto = new TeamMemberCampaignDto();
    teamMemberCampaignDto.setStatusUserCampaign(EStatusUserCampaign.INITIALIZED);

    Mockito.when(teamMemberService.updateTeamMemberCampaign("10",teamMemberCampaignDto))
            .thenReturn(teamMember);

    mockMvc.perform(patch("/teammembers/{teamMemberId}/statusCurrentCampaign","10")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(teamMemberCampaignDto)))
            .andExpect(status().isOk());
            //.andExpect(teamMember.getStatusCurrentCampaign().equals());
            //.andExpect(MockMvcResultMatchers.jsonPath("$.statusCurrentCampaign").value(EStatusUserCampaign.INITIALIZED.toString()));
  }

  @Test
  @WithUserDetails("teammemberfirst.teammemberlast")
  void testTeammember() throws Exception{
    TeamMember teamMember = new TeamMember();
    teamMember.setId(10);
    teamMember.setEmail("first.last@nnn.com");
    teamMember.setStatusCurrentCampaign(EStatusUserCampaign.OPENED);

    Mockito.when(teamMemberService.getTeamMember(10))
            .thenReturn(teamMember);

    mockMvc.perform(get("/teammembers/{teamMemberId}","10")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
                   // .andExpect(content().contentTypeCompatibleWith("application/json"))
                   // .andExpect(jsonPath("$.id").value(10));





  }

}
