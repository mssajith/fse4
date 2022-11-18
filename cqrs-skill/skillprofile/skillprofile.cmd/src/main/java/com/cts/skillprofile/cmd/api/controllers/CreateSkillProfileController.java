package com.cts.skillprofile.cmd.api.controllers;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skillprofile.cmd.api.commands.CreateSkillProfileCommand;
import com.cts.skillprofile.cmd.api.dto.CreateSkillProfileResponse;
import com.cts.skillprofile.cmd.exception.InvalidSkillRatingException;
import com.cts.skillprofile.common.dto.BaseResponse;
import com.cts.skillprofile.common.dto.Skill;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/v1/engineer/add-profile")
public class CreateSkillProfileController {
	private Logger logger = Logger.getLogger(CreateSkillProfileController.class.getName());

	@Autowired
	private CommandDispatcher commandDispatcher;

	@ApiOperation(value = "Create  SkillProfile")
	@PostMapping
	public ResponseEntity<BaseResponse> createSkillProfile(@Valid @RequestBody CreateSkillProfileCommand command) // {
			throws InvalidSkillRatingException {
		var id = UUID.randomUUID().toString();
		command.setId(id);
		try {
			// validate the expertise level
			List<Skill> skillList = command.getSkillList();

			// check if any of the skill rating is invalid
			Skill skill = skillList.stream().filter(skil -> {
				if (skil.getSkillRating() > 0 && skil.getSkillRating() <= 20) {
					return false;
				} else {
					return true;
				}
			}).findFirst().orElse(null);

			if (null != skill) {
				throw new InvalidSkillRatingException("Invalid Skill Rating " + skill.getSkillRating());
			}

			commandDispatcher.send(command);
			return new ResponseEntity<>(
					new CreateSkillProfileResponse("Skill Profile creation request completed successfully!", id),
					HttpStatus.CREATED);
		} catch (MethodArgumentNotValidException ex) {
			logger.log(Level.INFO, ex.getMessage());
			logger.log(Level.WARNING, MessageFormat.format("Argument validation failed - {0}", ex.toString()));
			return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
		} catch (IllegalStateException ex) {
			logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}", ex.toString()));
			return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {

			logger.log(Level.WARNING,
					MessageFormat.format("Error while processing create Skill Profile for id - {0}", id), ex);
			return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
