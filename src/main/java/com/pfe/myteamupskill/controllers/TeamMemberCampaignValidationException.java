package com.pfe.myteamupskill.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "One skill or more has a status to_be_trained")
public class TeamMemberCampaignValidationException extends RuntimeException {

}
