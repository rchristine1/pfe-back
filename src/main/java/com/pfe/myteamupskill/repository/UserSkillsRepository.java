package com.pfe.myteamupskill.repository;

import com.pfe.myteamupskill.models.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSkillsRepository extends CrudRepository<UserSkill,Integer> {
  List<UserSkill> findByTeamMember(TeamMember teamMember);
  List<UserSkill> findByTeamMemberAndStatusSkill(TeamMember teamMember,EStatusSkill statusSkill);

}

