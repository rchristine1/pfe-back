package com.pfe.myteamupskill.models;


import javax.persistence.*;

@Entity
@DiscriminatorValue("1")
public class TeamMember extends User{
  @ManyToOne
  @JoinColumn(name = "usermanagerid")
  //Fetch Lazy
  //@Column(name="usermanagerid")
  private Manager manager;
  @Column(name="userstatusvolunteertrainer")
  private EStatusVolunteer statusVolunteerTrainer;
  @Column(name="userstatuscurrentcampaign")
  private EStatusUserCampaign statusCurrentCampaign;
  @Column(name="userstatuslastcampaign")
  private EStatusUserCampaign statusLastCampaign;

  /*public TeamMember(String firstName,String lastName,String login,String password,String email) {

    super(firstName,lastName,login,password,email);
  }*/

  public TeamMember(){super();};

  public Manager getManager() {
    return manager;
  }

  public void setManager(Manager manager) {
    this.manager = manager;
  }

  public EStatusVolunteer getStatusVolunteerTrainer() {
    return statusVolunteerTrainer;
  }

  public void setStatusVolunteerTrainer(EStatusVolunteer statusVolunteerTrainer) {
    this.statusVolunteerTrainer = statusVolunteerTrainer;
  }

  public EStatusUserCampaign getStatusCurrentCampaign() {
    return statusCurrentCampaign;
  }

  public void setStatusCurrentCampaign(EStatusUserCampaign statusCurrentCampaign) {
    this.statusCurrentCampaign = statusCurrentCampaign;
  }

  public EStatusUserCampaign getStatusLastCampaign() {
    return statusLastCampaign;
  }

  public void setStatusLastCampaign(EStatusUserCampaign statusLastCampaign) {
    this.statusLastCampaign = statusLastCampaign;
  }
}
