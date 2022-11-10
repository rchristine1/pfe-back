package com.pfe.myteamupskill.payload.response;

import java.util.List;

public class JwtResponse {

  private String userName;
  private Integer id;
  private String firstname;
  private String lastname;
  private List<String> roles;

  public JwtResponse(String username, Integer id,String firstname,String lastname) {

    this.userName = username;
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;

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

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }
}