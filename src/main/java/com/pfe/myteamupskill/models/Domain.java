package com.pfe.myteamupskill.models;

import javax.persistence.*;

@Entity
public class Domain {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name="domainid")
  private int id;
  private String label;

  public Domain(){}

  public Domain(String label) {
    this.label = label;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
}
