package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.models.EStatusSkill;

public class UserSkillDto {
  private int userSkillId;
  private int userId;
  private String label;
  private String labelDomain;
  private String labelCampaign;
  private int mark;
  private int lastWriterId;
  private EStatusSkill statusSkill;


//pas de constructor objet technique

  public int getUserSkillId() {
    return userSkillId;
  }

  public void setUserSkillId(int userSkillId) {
    this.userSkillId = userSkillId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getLabelDomain() {
    return labelDomain;
  }

  public void setLabelDomain(String labelDomain) {
    this.labelDomain = labelDomain;
  }

  public String getLabelCampaign() {
    return labelCampaign;
  }

  public void setLabelCampaign(String labelCampaign) {
    this.labelCampaign = labelCampaign;
  }

  public int getMark() {
    return mark;
  }

  public void setMark(int mark) {
    if ((mark <=2) && (mark >=0 )) {
      this.mark = mark;
    } else {
      throw new IllegalArgumentException("Mark not accepted");
    }
  }

  public int getLastWriterId() {
    return lastWriterId;
  }

  public void setLastWriterId(int lastWriterId) {
    this.lastWriterId = lastWriterId;
  }

  public EStatusSkill getStatusSkill() {
    return statusSkill;
  }

  public void setStatusSkill(EStatusSkill statusSkill) {
    this.statusSkill = statusSkill;
  }
}