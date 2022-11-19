package com.pfe.myteamupskill.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

public class MyUser extends User {

  private final Integer id;
  private final String email;
  private final String lastname;
  private final String firstname;


  public MyUser(Integer id,String username, String password, Collection<? extends GrantedAuthority> authorities, String email, String lastname, String firstname) {
    super(username, password, authorities);
    this.id = id;
    this.email = email;
    this.lastname = lastname;
    this.firstname = firstname;
  }

  public String getEmail() {
    return email;
  }
  public String getLastname() {
    return lastname;
  }
  public String getFirstname() {
    return firstname;
  }
  public Integer getId() {
    return id;
  }


}
