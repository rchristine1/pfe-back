package com.pfe.myteamupskill.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_skills")
public class UserSkill {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="user_skillsid")
  private int id;

  @ManyToOne
  @JoinColumn(name = "userid",referencedColumnName = "userid")
  private TeamMember teamMember;
  @ManyToOne
  @JoinColumn(name = "skillid",referencedColumnName = "skillid")
  private Skill skill;
  @ManyToOne
  @JoinColumn(name = "campaignid",referencedColumnName = "campaignid")
  private Campaign campaign;
  private Integer mark;
  @Column(name = "lastwriterid")
  private int lastWriterId;
  @Column(name = "statusskill")
  private EStatusSkill statusSkill;

  public UserSkill() {  }

  public UserSkill(int id, TeamMember teamMember, Skill skill, Campaign campaign, Integer mark, int lastWriterId, EStatusSkill statusSkill) {
    this.id = id;
    this.teamMember = teamMember;
    this.skill = skill;
    this.campaign = campaign;
    this.mark = mark;
    this.lastWriterId = lastWriterId;
    this.statusSkill = statusSkill;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public TeamMember getTeamMember() {
    return teamMember;
  }

  public void setTeamMember(TeamMember teamMember) {
    this.teamMember = teamMember;
  }

  public Skill getSkill() {
    return skill;
  }

  public void setSkill(Skill skill) {
    this.skill = skill;
  }

  public Campaign getCampaign() {
    return campaign;
  }

  public void setCampaign(Campaign campaign) {
    this.campaign = campaign;
  }

  public Integer getMark() {
    return mark;
  }

  public void setMark(Integer mark) {
      this.mark = mark;
  }

  public int getLastWriterId() {
    return lastWriterId;
  }

  public void setLastWriterId(int lastWriterId) {
    this.lastWriterId = lastWriterId;
  }

  public EStatusSkill getStatusSkill() {
    return statusSkill;
  }

  public void setStatusSkill(EStatusSkill statusSkill) {
    this.statusSkill = statusSkill;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserSkill userSkill = (UserSkill) o;
    return Objects.equals(getTeamMember(), userSkill.getTeamMember()) && Objects.equals(getSkill(), userSkill.getSkill()) && Objects.equals(getCampaign(), userSkill.getCampaign());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTeamMember(), getSkill(), getCampaign());
  }
}
