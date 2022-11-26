package com.pfe.myteamupskill.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "UserSkill already exists")
public class UserSkillAlreadyExistException extends RuntimeException{
}
