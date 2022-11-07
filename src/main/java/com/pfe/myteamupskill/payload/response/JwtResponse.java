package com.pfe.myteamupskill.payload.response;

import java.util.List;

public class JwtResponse {

  private String userName;
  private int id;
  private List<String> roles;

  public JwtResponse(String username, int id) {

    this.userName = username;
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}