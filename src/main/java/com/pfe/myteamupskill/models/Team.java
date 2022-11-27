package com.pfe.myteamupskill.models;

import javax.persistence.*;

@Entity
@Table(name="team")
public class Team {
  @GeneratedValue
  @Id
  @Column(name="teamid")
  private Integer id;

  private String department;
  private String division;
  @Column(name="team")
  private String name;

  public Team() {
  }

  public Team(Integer id,String department,String division,String name) {
    this.id = id;
    this.department = department;
    this.division = division;
    this.name = name;
  }


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getDivision() {
    return division;
  }

  public void setDivision(String division) {
    this.division = division;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
