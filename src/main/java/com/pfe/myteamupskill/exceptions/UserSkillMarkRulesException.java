package com.pfe.myteamupskill.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Mark should be only 0,1 or 2")
public class UserSkillMarkRulesException extends RuntimeException {
}
