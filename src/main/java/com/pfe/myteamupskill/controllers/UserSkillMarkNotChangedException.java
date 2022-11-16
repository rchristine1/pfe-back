package com.pfe.myteamupskill.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Mark to update should be different")
public class UserSkillMarkNotChangedException extends RuntimeException{
}
