package com.pfe.myteamupskill.configuration;

import com.pfe.myteamupskill.authentication.MyUser;
import com.pfe.myteamupskill.models.TeamMember;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

  @Bean
  @Primary
  public UserDetailsService userDetailsService() {
    List<GrantedAuthority> authoritiesTeamMember = new ArrayList<>();

    authoritiesTeamMember.add(new SimpleGrantedAuthority("ROLE_USER"));
    authoritiesTeamMember.add(new SimpleGrantedAuthority("ROLE_TEAMMEMBER"));

    MyUser teamMemberUser = new MyUser(10,"teammemberfirst.teammemberlast","pwd",authoritiesTeamMember, "teammemberfirst.teammemberlast@company.com","teammemberfirst","teammemberlast" );
    List<GrantedAuthority> authoritiesTeamLeader = new ArrayList<>();

    authoritiesTeamLeader.add(new SimpleGrantedAuthority("ROLE_USER"));
    authoritiesTeamLeader.add(new SimpleGrantedAuthority("ROLE_TEAMLEADER"));
    authoritiesTeamLeader.add(new SimpleGrantedAuthority("ROLE_MANAGER"));

    MyUser teamLeaderUser = new MyUser(17,"teamleaderfirst.teamleaderlast","pwd",authoritiesTeamLeader, "teamleaderfirst.teamleaderlast@company.com","teamleaderfirst","teamleaderlast" );

    return new InMemoryUserDetailsManager(Arrays.asList(
            teamMemberUser, teamLeaderUser
    ));
  }
}
