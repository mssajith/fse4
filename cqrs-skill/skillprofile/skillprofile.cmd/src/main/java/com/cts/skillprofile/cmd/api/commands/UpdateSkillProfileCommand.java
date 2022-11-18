package com.cts.skillprofile.cmd.api.commands;

import java.util.Date;
import java.util.List;

import com.cts.skillprofile.common.dto.Skill;
import com.techbank.cqrs.core.commands.BaseCommand;

import lombok.Data;

@Data
public class UpdateSkillProfileCommand extends BaseCommand {
	private double amount;
	private List<Skill> skillProfile;
	private Date skillUpdatedDate;
}
