package com.pfe.myteamupskill.repository;

import com.pfe.myteamupskill.models.EStatusUserCampaign;
import com.pfe.myteamupskill.models.Manager;
import com.pfe.myteamupskill.models.TeamMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends CrudRepository<TeamMember,Integer> {
  TeamMember findOneByLogin(String login);
  List<TeamMember> findAll();
  List<TeamMember> findByStatusCurrentCampaign(EStatusUserCampaign status);
  TeamMember findOneById(Integer id);
}
