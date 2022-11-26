package com.pfe.myteamupskill.controllers.dto;

import com.pfe.myteamupskill.models.EStatusUserCampaign;
import com.pfe.myteamupskill.models.EStatusVolunteer;


public class TeamMemberDto extends UserDto {
  private String fullNameManager;
  private int managerId;
  private EStatusVolunteer statusVolunteerTrainer;
  private EStatusUserCampaign statusCurrentCampaign;
  private EStatusUserCampaign statusLastCampaign;

  public String getFullNameManager() {
    return fullNameManager;
  }

  public void setFullNameManager(String fullNameManager) {
    this.fullNameManager = fullNameManager;
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

  public int getManagerId() {
    return managerId;
  }

  public void setManagerId(int managerId) {
    this.managerId = managerId;
  }
}
