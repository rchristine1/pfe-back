package com.pfe.myteamupskill.controllers;

public class ManagerDto extends UserDto {
  private String team;

  public String getTeam() {
    return team;
  }

  public void setTeam(String team) {
    this.team = team;
  }
}
