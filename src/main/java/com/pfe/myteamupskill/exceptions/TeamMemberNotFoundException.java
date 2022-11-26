package com.pfe.myteamupskill.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "TeamMember not found.")
public class TeamMemberNotFoundException extends RuntimeException{
}
