package com.pfe.myteamupskill.services;

import com.pfe.myteamupskill.models.User;
import com.pfe.myteamupskill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;

  public User findOneByLogin(String login) {
    User user = userRepository.findOneByLogin(login);
    return user;
  }

  public User getUser(int id) {
    Optional<User> userOptional=userRepository.findById(id);
    if (userOptional.isPresent())
      return userOptional.get();
    else
      throw new IllegalArgumentException("User non existing");
  }

  public Integer getUserConnectedId(Principal principal) {
    if (!(principal instanceof UsernamePasswordAuthenticationToken)) {
      throw new RuntimeException(("User not found"));
    }
    UsernamePasswordAuthenticationToken userConnected = (UsernamePasswordAuthenticationToken) principal;
    User oneByLogin = findOneByLogin(userConnected.getName());
    return oneByLogin.getId();
  }

}
