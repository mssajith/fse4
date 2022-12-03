package com.cts.skillprofile.common.dto;

public enum NonTechSkill {
	SPOKEN("SPOKEN"),
	COMMUNICATION("COMMUNICATION"),
	APTITUDE("APTITUDE");
	
	private String skillName;
	private NonTechSkill(String skillName) {
		this.setSkillName(skillName);
	}
	
	public static NonTechSkill valueOfSkill(String skillName) {
		for(NonTechSkill skill : values()) {
			if (skill.getSkillName().equalsIgnoreCase(skillName)) {
				return skill;
			}
		}
		return null;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
}
