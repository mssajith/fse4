package com.cts.skillprofile.common.dto;

public enum TechSkill {
	HTML_CSS_JAVASCRIPT("HTML-CSS-JAVASCRIPT"), 
	ANGULAR("ANGULAR"),
	REACT("REACT"),
	SPRING("SPRING"),
	RESTFUL("RESTFUL"),
	HIBERNATE("HIBERNATE"),
	GIT("GIT"),
	DOCKER("DOCKER"),
	JENKINS("JENKINS"), 
	AWS("AWS");
	
	private String skill;
	
	private TechSkill(String skill) {
		this.setSkill(skill);
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	
	public static TechSkill valueOfSkill(String skillName) {
		for(TechSkill skill : values()) {
			if (skill.getSkill().equalsIgnoreCase(skillName)) {
				return skill;
			}
		}
		return null;
	}
	
}
