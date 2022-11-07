package com.pfe.myteamupskill.models;

import javax.persistence.*;

@Entity
@DiscriminatorValue("2")
public class Manager extends User{
  private Integer level;

  @OneToOne
  @JoinColumn(name = "managerteamid", referencedColumnName = "teamid")
  private Team team;

  public Manager(){super();}
  public Manager(Integer level, Team team) {
    super();
    this.level = level;
    this.team = team;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }
}
