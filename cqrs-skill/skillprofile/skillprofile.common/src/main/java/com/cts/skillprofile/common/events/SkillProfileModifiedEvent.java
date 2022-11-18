package com.cts.skillprofile.common.events;

import java.util.Date;
import java.util.List;

import com.cts.skillprofile.common.dto.Skill;
import com.techbank.cqrs.core.events.BaseEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SkillProfileModifiedEvent extends BaseEvent{
	private double amount;
	private List<Skill> skillList;
	private Date skillUpdatedDate;
}
