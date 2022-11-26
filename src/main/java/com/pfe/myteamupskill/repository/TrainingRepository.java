package com.pfe.myteamupskill.repository;

import com.pfe.myteamupskill.models.Campaign;
import com.pfe.myteamupskill.models.Training;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainingRepository extends CrudRepository<Training,Integer> {
  List<Training> findBySkill_Label(String label);

}
