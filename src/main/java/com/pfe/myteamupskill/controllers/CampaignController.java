package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.models.Campaign;
import com.pfe.myteamupskill.models.EStatusCampaign;
import com.pfe.myteamupskill.models.Manager;
import com.pfe.myteamupskill.services.CampaignService;
import com.pfe.myteamupskill.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class CampaignController {
  @Autowired
  UserService userService;

  @Autowired
  CampaignService campaignService;

  @GetMapping(value = "/campaign/status")
  //@PreAuthorize("hasRole('TEAMLEADER')")
  public ResponseEntity campaigncurrent(Principal principal, @RequestParam(required = false) String status) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    Campaign campaignSelected = campaignService.getCampaignCurrent(EStatusCampaign.valueOf(status));
    if (campaignSelected == null) {
      return new ResponseEntity("Campaign not existing", HttpStatus.NOT_FOUND);
    }
    CampaignDto campaignDto = new CampaignDto();
    campaignDto.setLabel(campaignSelected.getLabel());
    campaignDto.setId(campaignSelected.getId());

    return new ResponseEntity(campaignDto, HttpStatus.OK);
  }
}
