package com.pfe.myteamupskill.repository;

import com.pfe.myteamupskill.models.Domain;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DomainRepository extends CrudRepository<Domain,Integer> {
  List<Domain> findAll();
  Optional<Domain> findByLabel(String label);
}
