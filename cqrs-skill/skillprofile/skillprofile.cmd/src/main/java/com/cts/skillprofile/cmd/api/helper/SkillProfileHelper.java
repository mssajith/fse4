package com.cts.skillprofile.cmd.api.helper;

import java.util.List;

import com.cts.skillprofile.cmd.exception.InvalidSkillNameException;
import com.cts.skillprofile.cmd.exception.InvalidSkillRatingException;
import com.cts.skillprofile.common.dto.NonTechSkill;
import com.cts.skillprofile.common.dto.Skill;
import com.cts.skillprofile.common.dto.SkillType;
import com.cts.skillprofile.common.dto.TechSkill;

public class SkillProfileHelper {
	public static void validateSkillRating(List<Skill> skillList) throws InvalidSkillRatingException {
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
	}

	public static void validateSkillName(List<Skill> skillList) throws InvalidSkillNameException {
		for(Skill skill: skillList) {
			if (skill.getSkillType() == SkillType.TECHNICAL) {
				TechSkill techSkill = TechSkill.valueOfSkill(skill.getSkillName());
				if (null == techSkill) {
					throw new InvalidSkillNameException("Invalid Skill Name : " + skill.getSkillName());
				} 
			} else if (skill.getSkillType() == SkillType.NONTECHNICAL) {
				NonTechSkill nonTechSkill = NonTechSkill.valueOfSkill(skill.getSkillName());
				if (null == nonTechSkill) {
					throw new InvalidSkillNameException("Invalid Skill Name : " + skill.getSkillName());
				}
			}
		}
	}
}
