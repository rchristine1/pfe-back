package com.pfe.myteamupskill.models;

import javax.persistence.*;

@Entity
@Table(name="campaign")
public class Campaign {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="campaignid")
  private int id;
  private String label;
  private EStatusCampaign status;

  public Campaign() {
  }

  public Campaign(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public EStatusCampaign getStatus() {
    return status;
  }

  public void setStatus(EStatusCampaign status) {
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
