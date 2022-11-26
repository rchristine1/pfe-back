package com.pfe.myteamupskill.controllers;

import com.pfe.myteamupskill.controllers.dto.ManagerDto;
import com.pfe.myteamupskill.models.Manager;
import com.pfe.myteamupskill.security.jwt.JwtController;
import com.pfe.myteamupskill.security.jwt.JwtUtils;
import com.pfe.myteamupskill.services.ManagerService;
import com.pfe.myteamupskill.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class ManagerController {
  @Autowired
  ManagerService managerService;

  @Autowired
  JwtController jwtController;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UserService userService;

  @PreAuthorize("hasRole('ROLE_TEAMLEADER')")
  @GetMapping(value = "/managers/{managerId}")
  public ResponseEntity manager(Principal principal, @PathVariable("managerId") String managerId) {
    Integer userConnectedId = userService.getUserConnectedId(principal);
    Integer managerIdValue = Integer.valueOf(managerId);
    Manager managerSelected = managerService.findById(managerIdValue);
    if (managerSelected == null) {
      return new ResponseEntity("Manager not found", HttpStatus.NOT_FOUND);
    }
    ManagerDto managerDto = new ManagerDto();
    managerDto.setTeam(managerSelected.getTeam().getDepartment()
            +'-'+managerSelected.getTeam().getDivision()
            +'-'+managerSelected.getTeam().getName());

    return new ResponseEntity(managerDto, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_TEAMLEADER')")
  @PostMapping("/managers/")
  public ResponseEntity add(@Valid @RequestBody Manager userEntity) {
    Manager existingUser = managerService.findOneByLogin(userEntity.getLogin());
    if (existingUser != null) {
      return new ResponseEntity("User already existing", HttpStatus.BAD_REQUEST);
    }
    Manager user = managerService.saveUser(userEntity);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }
}
