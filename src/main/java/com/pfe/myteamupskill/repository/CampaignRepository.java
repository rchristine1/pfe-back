package com.pfe.myteamupskill.repository;

import com.pfe.myteamupskill.models.Campaign;
import com.pfe.myteamupskill.models.EStatusCampaign;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CampaignRepository extends CrudRepository<Campaign,Integer> {
  Optional<Campaign> findByStatus(EStatusCampaign eStatusCampaign);
  Optional<Campaign> findByLabel(String labelCampaign);

}
