package com.pfe.myteamupskill.services;

import com.pfe.myteamupskill.models.Manager;
import com.pfe.myteamupskill.repository.ManagerRepository;
import com.pfe.myteamupskill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ManagerService {
  @Autowired
  ManagerRepository managerRepository;

  @Autowired
  UserRepository userRepository;

  public Manager findOneByLogin(String login) {
    Manager manager = managerRepository.findOneByLogin(login);
    return manager;
  }

  public Manager saveUser(Manager userEntity) {
    Manager user = new Manager();
    user.setLogin(userEntity.getLogin());
    user.setPassword(new BCryptPasswordEncoder().encode(userEntity.getPassword()));
    user.setLastName(StringUtils.capitalize(userEntity.getLastName()));
    user.setFirstName(StringUtils.capitalize(userEntity.getFirstName()));
    user.setEmail(userEntity.getEmail());
    user.setLevel(userEntity.getLevel());
    managerRepository.save(user);
    return user;
  }
}
