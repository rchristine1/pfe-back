package com.pfe.myteamupskill.payload.request;

public class JwtRequest {

  private String login;
  private String password;


  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
