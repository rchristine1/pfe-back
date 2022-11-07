package com.pfe.myteamupskill.services;

import com.pfe.myteamupskill.models.Domain;
import com.pfe.myteamupskill.models.Skill;
import com.pfe.myteamupskill.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
  @Autowired
  SkillRepository skillRepository;

  @Autowired
  DomainService domainService;

  public List<Skill> findAll() {
    List<Skill> skills = skillRepository.findAll();
    return skills;
  }

  public List<Skill> getDomainSkills(String label) {
    Domain domain = domainService.getDomainByLabel(label);
    List<Skill> domainSkills = skillRepository.findByDomain(domain);
    return domainSkills;
  }



}
