package com.cts.skillprofile.cqrs.core.handlers;

import com.cts.skillprofile.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler <T> {
	void save(AggregateRoot aggregate);
	T getById(String id);
}
