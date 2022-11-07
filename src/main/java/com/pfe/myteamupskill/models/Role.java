package com.pfe.myteamupskill.models;

import javax.persistence.*;

@Entity
@Table(name="role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="roleid")
  private int id;

  @Enumerated(EnumType.STRING)
  private ERole name;

  public Role() {
  }

  public Role(ERole name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public ERole getName() {
    return name;
  }

  public void setName(ERole name) {
    this.name = name;
  }
}
