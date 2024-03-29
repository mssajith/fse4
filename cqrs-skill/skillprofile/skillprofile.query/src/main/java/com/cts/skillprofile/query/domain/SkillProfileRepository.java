package com.cts.skillprofile.query.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cts.skillprofile.cqrs.core.domain.BaseEntity;

public interface SkillProfileRepository extends CrudRepository<SkillProfile, String>{
//	Optional<SkillProfile> findByAccountHolder(String accountHolder);
//	List<BaseEntity> findByBalanceGreaterThan(double balance);
//	List<BaseEntity> findByBalanceLessThan(double balance);
	
	List<BaseEntity> findByNameStartsWith(String name);
	List<BaseEntity> findByAssociateId(String associateId);
}
