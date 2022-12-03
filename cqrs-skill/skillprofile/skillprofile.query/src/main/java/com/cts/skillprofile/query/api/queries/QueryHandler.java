package com.cts.skillprofile.query.api.queries;

import java.util.List;

import com.cts.skillprofile.cqrs.core.domain.BaseEntity;


public interface QueryHandler {
	List<BaseEntity> handle(FindAllAccountsQuery query);
	List<BaseEntity> handle(FindAccountByIdQuery query);
	
	List<BaseEntity> handle(FindSkillProfileByNameQuery query);
	
	List<BaseEntity> handle(FindSkillProfileByAssociateIdQuery query);
	List<BaseEntity> handle(FindSkillProfileBySkillNameQuery query);
}
