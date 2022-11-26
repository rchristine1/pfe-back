package com.pfe.myteamupskill.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "TeamMember already exists.")
public class TeamMemberAlreadyExistingException extends RuntimeException{
}
