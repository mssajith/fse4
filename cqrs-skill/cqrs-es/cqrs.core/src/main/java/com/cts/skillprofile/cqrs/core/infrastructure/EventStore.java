package com.cts.skillprofile.cqrs.core.infrastructure;

import java.util.List;

import com.cts.skillprofile.cqrs.core.events.BaseEvent;

public interface EventStore {
	void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);
	List<BaseEvent> getEvents(String aggregateId);

}
