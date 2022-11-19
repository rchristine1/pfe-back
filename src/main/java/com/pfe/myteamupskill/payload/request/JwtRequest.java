package com.pfe.myteamupskill.payload.request;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

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
