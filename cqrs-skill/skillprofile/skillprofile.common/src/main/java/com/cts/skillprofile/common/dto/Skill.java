package com.cts.skillprofile.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Skill {
	private String skillName;
	private Integer skillRating;
	private SkillType skillType;
}
