package com.pfe.myteamupskill.repository;

import com.pfe.myteamupskill.models.ERole;
import com.pfe.myteamupskill.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleTRepository extends CrudRepository<Role,Integer> {
  Optional<Role> findByName(ERole name);
}
