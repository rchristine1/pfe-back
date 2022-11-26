package com.pfe.myteamupskill.services;

import com.pfe.myteamupskill.exceptions.CampaignBadStatusException;
import com.pfe.myteamupskill.exceptions.CampaignNotFoundException;
import com.pfe.myteamupskill.models.Campaign;
import com.pfe.myteamupskill.models.EStatusCampaign;
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
      throw new IllegalArgumentException("Campaign not found");
  }

  public Campaign findCampaignByLabel(String label) {
    Optional<Campaign> campaignOptional=campaignRepository.findByLabel(label);
    if (campaignOptional.isPresent())
      return campaignOptional.get();
    else
      throw new IllegalArgumentException("Campaign not found");
  }

  public Campaign getCampaign(EStatusCampaign status) {
    if(status != null) {
      Optional<Campaign> campaignOptional = campaignRepository.findByStatus(status);
      if (campaignOptional.isPresent())
        return campaignOptional.get();
      else
        throw new CampaignNotFoundException();
    } else {throw new CampaignBadStatusException();}
  }
}
