package com.pfe.myteamupskill.models;

import javax.persistence.*;

@Entity
@Table(name="skill")
public class Skill {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name="skillid")
  private Integer id;

  private String label;
  @ManyToOne
  @JoinColumn(name="skilldomainid",referencedColumnName = "domainid")
  private Domain domain;

  public Skill() {
  }

  public Skill(String label, Domain domain) {
    this.id = id;
    this.label = label;
    this.domain = domain;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Domain getDomain() {
    return domain;
  }

  public void setDomain(Domain domain) {
    this.domain = domain;
  }
}
