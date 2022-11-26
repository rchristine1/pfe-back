package com.pfe.myteamupskill.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "One skill or more has a status to_be_trained")
public class TeamMemberCampaignValidationException extends RuntimeException {

}
