package com.pfe.myteamupskill.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name="user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type",
        discriminatorType = DiscriminatorType.INTEGER)
//public class User {
public abstract class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="userid")
  private int id;

  @NotBlank
  @Size(min = 2, max = 25, message = "Lastname Entre 2 et 25 caracteres SVP")
  @Column(name="firstname")
  private String firstName;

  @NotBlank
  @Size(min = 2, max = 25, message = "Firstname Entre 2 et 25 caracteres SVP")
  @Column(name="lastname")
  private String lastName;

  @NotBlank
  @Size(max = 60)
  private String login;

  private String password;

  @Email
  private String email;

  private String picture;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(name="user_roles",
          joinColumns = @JoinColumn(name="userid"),
          inverseJoinColumns = @JoinColumn(name="roleid"))
  private Set<Role> roles ;


  public User() {
  }

  public User(String firstName,String lastName,String login) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.login = login;
  }

  public User(String firstName, String lastName, String login, String password, String email) {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public Set<Role> getRoles() {
    return roles;
  }
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  /*public int getUserType() {
    return user_type;
  }

  public void setUserType(int userType) {
    this.userType = userType;
  }*/
}