package com.pfe.myteamupskill.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "No skill with status to_be_trained.")
public class TeamMemberCampaignInProgressException extends RuntimeException {

}
