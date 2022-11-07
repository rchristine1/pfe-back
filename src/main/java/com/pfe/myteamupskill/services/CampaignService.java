package com.pfe.myteamupskill.services;

import com.pfe.myteamupskill.models.Campaign;
import com.pfe.myteamupskill.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CampaignService {
  @Autowired
  CampaignRepository campaignRepository;

  public Campaign getCampaign(int id) {
    Optional<Campaign> campaignOptional=campaignRepository.findById(id);
    if (campaignOptional.isPresent())
      return campaignOptional.get();
    else
      throw new IllegalArgumentException("campaign not found");
  }
}
