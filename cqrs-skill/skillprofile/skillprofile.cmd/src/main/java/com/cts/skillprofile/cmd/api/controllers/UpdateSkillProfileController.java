package com.cts.skillprofile.cmd.api.controllers;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cts.skillprofile.cmd.api.commands.UpdateSkillProfileCommand;
import com.cts.skillprofile.cmd.api.helper.SkillProfileHelper;
import com.cts.skillprofile.cmd.exception.SkillUpdationException;
import com.cts.skillprofile.common.domain.SkillProfileServiceResponse;
import com.cts.skillprofile.common.domain.SkillProfileVO;
import com.cts.skillprofile.common.dto.BaseResponse;
import com.cts.skillprofile.common.dto.Skill;
import com.cts.skillprofile.cqrs.core.exceptions.AggregateNotFoundException;
import com.cts.skillprofile.cqrs.core.infrastructure.CommandDispatcher;

@RestController
@RequestMapping(path = "/api/v1/engineer/update-profile")
public class UpdateSkillProfileController {
	private final Logger logger = Logger.getLogger(UpdateSkillProfileController.class.getName());

	@Autowired
	private CommandDispatcher commandDispatcher;


	@PutMapping(path = "/{id}")
	public ResponseEntity<BaseResponse> updateProfile(@PathVariable(value = "id") String id,
			@RequestBody UpdateSkillProfileCommand command) {
		try {
			validteLastUpdation(id);
			List<Skill> skillList = command.getSkillProfile();
			
			//validate Skill Name
			SkillProfileHelper.validateSkillName(skillList);
			
			// check if any of the skill rating is invalid
			SkillProfileHelper.validateSkillRating(skillList);
			
			command.setId(id);
			commandDispatcher.send(command);
			return new ResponseEntity<>(new BaseResponse("Skill Profile Updation Request Completed Successfully"),
					HttpStatus.OK);
		} catch (IllegalStateException | AggregateNotFoundException ex) {
			logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}", ex.toString()));
			return new ResponseEntity<>(new BaseResponse(ex.toString()), HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			String errorMsg = StringUtils.isEmpty(ex.getMessage()) ?
					MessageFormat.format("Error while processing request to update Skill Profile with id - {0}", id): ex.getMessage()+" with id "+id;
			logger.log(Level.WARNING, errorMsg, ex);
			return new ResponseEntity<>(new BaseResponse(errorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private void validteLastUpdation(String id) {
		
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://skillprofilecmdapp-env.eba-pajjh5f2.us-east-2.elasticbeanstalk.com/api/v1/admin/byId/"+id;
		SkillProfileServiceResponse skillPrifleResp = restTemplate.getForObject(url, SkillProfileServiceResponse.class);
		
		if (null != skillPrifleResp) {
			List<SkillProfileVO> skillProfileList = skillPrifleResp.getSkillProfiles();
			Date skillUpdatedDate = skillProfileList.get(0).getSkillUpdatedDate();
			long diffInMillies = Math.abs(new Date().getTime() - skillUpdatedDate.getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			
			if (diff < 10) {
				throw new SkillUpdationException("You cannot update the skill that is less than 10 days old");
			}
			
		}
	}
}
