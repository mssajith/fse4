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
import com.cts.skillprofile.cmd.api.helper.SkillProfileHelper;
import com.cts.skillprofile.cmd.exception.InvalidSkillNameException;
import com.cts.skillprofile.cmd.exception.InvalidSkillRatingException;
import com.cts.skillprofile.common.dto.BaseResponse;
import com.cts.skillprofile.common.dto.NonTechSkill;
import com.cts.skillprofile.common.dto.Skill;
import com.cts.skillprofile.common.dto.SkillType;
import com.cts.skillprofile.common.dto.TechSkill;
import com.cts.skillprofile.cqrs.core.infrastructure.CommandDispatcher;

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
			List<Skill> skillList = command.getSkillList();
			
			//validate Skill Name
			SkillProfileHelper.validateSkillName(skillList);
			
			// check if any of the skill rating is invalid
			SkillProfileHelper.validateSkillRating(skillList);

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
