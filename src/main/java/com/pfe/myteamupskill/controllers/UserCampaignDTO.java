package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.models.EStatusCampaign;

public class UserCampaignDTO {

  private EStatusCampaign status;

  public EStatusCampaign getStatus() {
    return status;
  }

  public void setStatus(EStatusCampaign status) {
    this.status = status;
  }
}
