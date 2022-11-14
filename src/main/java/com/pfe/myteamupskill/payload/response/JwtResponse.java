package com.pfe.myteamupskill.payload.response;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {

  private String userName;
  private Integer id;
  private String firstname;
  private String lastname;
  private Collection<? extends GrantedAuthority> authorities;

  public JwtResponse(String username, Integer id,String firstname,String lastname,Collection<? extends GrantedAuthority> authorities) {

    this.userName = username;
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.authorities = authorities;

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

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Collection<? extends GrantedAuthority> roles) {
    this.authorities = authorities;
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