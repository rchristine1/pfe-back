package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.models.EStatusUserCampaign;

public class TeamMemberCampaignDto {
  private EStatusUserCampaign statusUserCampaign;

  public EStatusUserCampaign getStatusUserCampaign() {
    return statusUserCampaign;
  }

  public void setStatusUserCampaign(EStatusUserCampaign statusUserCampaign) {
    this.statusUserCampaign = statusUserCampaign;
  }
}
