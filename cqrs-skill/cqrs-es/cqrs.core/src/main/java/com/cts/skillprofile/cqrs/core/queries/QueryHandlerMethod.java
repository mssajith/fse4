package com.cts.skillprofile.cqrs.core.queries;

import java.util.List;

import com.cts.skillprofile.cqrs.core.domain.BaseEntity;

@FunctionalInterface
public interface QueryHandlerMethod <T extends BaseQuery>{
	
List<BaseEntity> handle(T query);
}
