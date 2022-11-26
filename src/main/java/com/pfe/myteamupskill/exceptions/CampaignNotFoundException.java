package com.pfe.myteamupskill.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "Campaign not found.")
public class CampaignNotFoundException extends RuntimeException{
}
