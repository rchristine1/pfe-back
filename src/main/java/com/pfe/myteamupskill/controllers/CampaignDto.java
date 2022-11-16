package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.models.EStatusCampaign;

public class CampaignDto {

  private int id;
  private String status;
  private String label;


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
