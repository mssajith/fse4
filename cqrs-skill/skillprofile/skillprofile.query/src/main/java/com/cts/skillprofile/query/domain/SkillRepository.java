package com.cts.skillprofile.query.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface SkillRepository extends CrudRepository<SkillEntity, String>{ 
	List<SkillEntity> findBySkillName(String skillName);
}
