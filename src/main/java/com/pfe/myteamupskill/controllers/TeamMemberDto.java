package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.models.EStatusUserCampaign;
import com.pfe.myteamupskill.models.EStatusVolunteer;
import com.pfe.myteamupskill.models.Manager;


public class TeamMemberDto extends UserDto {
  private String manager;
  private EStatusVolunteer statusVolunteerTrainer;
  private EStatusUserCampaign statusCurrentCampaign;
  private EStatusUserCampaign statusLastCampaign;

  public String getManager() {
    return manager;
  }

  public void setManager(String manager) {
    this.manager = manager;
  }

  public EStatusVolunteer getStatusVolunteerTrainer() {
    return statusVolunteerTrainer;
  }

  public void setStatusVolunteerTrainer(EStatusVolunteer statusVolunteerTrainer) {
    this.statusVolunteerTrainer = statusVolunteerTrainer;
  }

  public EStatusUserCampaign getStatusCurrentCampaign() {
    return statusCurrentCampaign;
  }

  public void setStatusCurrentCampaign(EStatusUserCampaign statusCurrentCampaign) {
    this.statusCurrentCampaign = statusCurrentCampaign;
  }

  public EStatusUserCampaign getStatusLastCampaign() {
    return statusLastCampaign;
  }

  public void setStatusLastCampaign(EStatusUserCampaign statusLastCampaign) {
    this.statusLastCampaign = statusLastCampaign;
  }


}