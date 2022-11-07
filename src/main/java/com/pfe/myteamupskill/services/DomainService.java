package com.pfe.myteamupskill.services;

import com.pfe.myteamupskill.models.Domain;
import com.pfe.myteamupskill.repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomainService {
  @Autowired
  DomainRepository domainRepository;

  public List<Domain> getDomains() {
    List<Domain> domains = domainRepository.findAll();
    return domains;
  }

  public Domain getDomainByLabel(String label) {
    Optional<Domain> domainOptional=domainRepository.findByLabel(label);
    if (domainOptional.isPresent())
      return domainOptional.get();
    else
      throw new IllegalArgumentException("Domain not existing");
  }

}
