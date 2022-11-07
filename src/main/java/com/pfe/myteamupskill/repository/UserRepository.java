package com.pfe.myteamupskill.repository;

import com.pfe.myteamupskill.models.TeamMember;
import com.pfe.myteamupskill.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer>{
  User findOneByLogin(String login);
  User findOneById(Integer id);
}
