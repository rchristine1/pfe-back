package com.pfe.myteamupskill.repository;


import com.pfe.myteamupskill.models.Domain;
import com.pfe.myteamupskill.models.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SkillRepository extends CrudRepository<Skill,Integer> {
  List<Skill> findAll();
  List<Skill> findByDomain(Domain domain);

}
