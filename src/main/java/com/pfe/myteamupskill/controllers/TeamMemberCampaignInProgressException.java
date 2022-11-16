package com.pfe.myteamupskill.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "No skill with status to_be_trained.")
public class TeamMemberCampaignInProgressException extends RuntimeException {

}
