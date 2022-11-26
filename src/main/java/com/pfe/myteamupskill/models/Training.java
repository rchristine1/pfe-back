package com.pfe.myteamupskill.models;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name="training")
public class Training {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="trainingid")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="skillid",referencedColumnName = "skillid")
    private Skill skill;
    @ManyToOne
    @JoinColumn(name = "campaignid",referencedColumnName = "campaignid")
    private Campaign campaign;
    @ManyToOne
    @JoinColumn(name="trainerid",referencedColumnName = "userid")
    private TeamMember teamMember;
    @Column(name="statustraining")
    private EStatusTraining status;
    @Column(name="startdate")
    private Date startDate;
    @Column(name="enddate")
    private Date endDate;
    @ManyToOne
    @JoinColumn(name="managerid",referencedColumnName = "userid")
    private Manager manager;

    public Training() {
    }

    public Training(Skill skill) {
      this.skill = skill;
    }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Skill getSkill() {
    return skill;
  }

  public void setSkill(Skill skill) {
    this.skill = skill;
  }

  public TeamMember getTeamMember() {
    return teamMember;
  }

  public void setTeamMember(TeamMember teamMember) {
    this.teamMember = teamMember;
  }

  public EStatusTraining getStatus() {
    return status;
  }

  public void setStatus(EStatusTraining status) {
    this.status = status;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Manager getManager() {
    return manager;
  }

  public void setManager(Manager manager) {
    this.manager = manager;
  }

  public Campaign getCampaign() {
    return campaign;
  }

  public void setCampaign(Campaign campaign) {
    this.campaign = campaign;
  }
}
