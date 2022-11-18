package com.cts.skillprofile.cmd.api.commands;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.cts.skillprofile.common.dto.Skill;
import com.techbank.cqrs.core.commands.BaseCommand;

import lombok.Data;

@Data
public class CreateSkillProfileCommand extends BaseCommand{
	
    @NotEmpty(message = "Name may not be null")
    @Size(min = 5, max = 30, message = "validation error for Name length should be between 5 to 30")
	private String name;
    
    @NotEmpty(message = "associateid may not be null")
    @Pattern(regexp = "^CTS.*$", message = "ID must start with CTS", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String associateId;
    
    @NotEmpty(message = "email may not be null")
    @Email(message = "Please provide a valid email address")
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
	private String email;
    
    @NotNull(message = "mobile may not be null")
	private Long mobile;
	private List<Skill> skillList;
	private Date skillCreatedDate;
	private Date skillUpdatedDate;
	
}
