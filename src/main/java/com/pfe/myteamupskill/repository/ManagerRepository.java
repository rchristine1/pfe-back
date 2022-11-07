package com.pfe.myteamupskill.repository;

import com.pfe.myteamupskill.models.Manager;
import com.pfe.myteamupskill.models.TeamMember;
import org.springframework.data.repository.CrudRepository;

public interface ManagerRepository extends CrudRepository<Manager,Integer> {
  Manager findOneByLogin(String login);
}
