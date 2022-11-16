package com.pfe.myteamupskill.authentication;

import com.pfe.myteamupskill.models.ERole;
import com.pfe.myteamupskill.models.Manager;
import com.pfe.myteamupskill.models.TeamMember;
import com.pfe.myteamupskill.models.User;
import com.pfe.myteamupskill.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  //appeler userService
  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    User user = userRepository.findOneByLogin(login);
    List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .collect(Collectors.toList());
    if ( user instanceof TeamMember) {
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
      authorities.add(new SimpleGrantedAuthority("ROLE_TEAMMEMBER"));
    } else if (user instanceof Manager) {
      authorities.add(new SimpleGrantedAuthority("ROLE_TEAMLEADER"));
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
      authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
    }
    MyUser userDetails = new MyUser(user.getId(),user.getLogin(),user.getPassword(),authorities, user.getEmail(),user.getLastName(), user.getFirstName());
    System.out.println("loadUserByUsrName :Je passe par ici");
    /*final List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));*/
    //return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    return userDetails;
  }
}
