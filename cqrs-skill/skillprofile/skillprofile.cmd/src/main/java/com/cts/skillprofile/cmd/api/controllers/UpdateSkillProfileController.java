package com.cts.skillprofile.cmd.api.controllers;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skillprofile.cmd.api.commands.UpdateSkillProfileCommand;
import com.cts.skillprofile.common.dto.BaseResponse;
import com.techbank.cqrs.core.exceptions.AggregateNotFoundException;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

@RestController
@RequestMapping(path = "/api/v1/engineer/update-profile")
public class UpdateSkillProfileController {
	private final Logger logger = Logger.getLogger(UpdateSkillProfileController.class.getName());

	@Autowired
	private CommandDispatcher commandDispatcher;

	@PutMapping(path = "/{id}")
public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String id,
		@RequestBody UpdateSkillProfileCommand command) {
	try {
	command.setId(id);
	commandDispatcher.send(command);
	return new ResponseEntity<>(new BaseResponse("Deposit Funds Request Completed Successfully"), HttpStatus.OK);
	} catch (IllegalStateException | AggregateNotFoundException ex) {
		logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}", ex.toString()));
		return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
	} catch (Exception ex) {
		String errorMsg = MessageFormat.format("Error while processing request to deposit funds to bank account with id - {0}", id);
		logger.log(Level.WARNING, errorMsg, ex);
		return new ResponseEntity<>(new BaseResponse(errorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
}
