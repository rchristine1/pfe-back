package com.pfe.myteamupskill.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Mark should be only 0,1 or 2")
public class UserSkillMarkRulesException extends RuntimeException {
}
