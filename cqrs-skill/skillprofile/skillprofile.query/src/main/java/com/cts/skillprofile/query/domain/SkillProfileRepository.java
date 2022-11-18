package com.cts.skillprofile.query.domain;

import java.util.List;
import java.util.Optional;
import com.techbank.cqrs.core.domain.BaseEntity;

import org.springframework.data.repository.CrudRepository;

public interface SkillProfileRepository extends CrudRepository<SkillProfile, String>{
	Optional<SkillProfile> findByAccountHolder(String accountHolder);
	List<BaseEntity> findByBalanceGreaterThan(double balance);
	List<BaseEntity> findByBalanceLessThan(double balance);
	
	List<BaseEntity> findByNameStartsWith(String name);
	List<BaseEntity> findByAssociateId(String associateId);
}
