package com.cts.skillprofile.cmd.infrastructure;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.skillprofile.cmd.domain.EventStoreRepository;
import com.cts.skillprofile.cmd.domain.SkillProfileAggregate;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.events.EventModel;
import com.techbank.cqrs.core.exceptions.AggregateNotFoundException;
import com.techbank.cqrs.core.exceptions.ConcurrencyException;
import com.techbank.cqrs.core.infrastructure.EventStore;
import com.techbank.cqrs.core.producers.EventProducer;

@Service
public class SkillProfileEventStore implements EventStore {
	@Autowired
	private EventStoreRepository eventStoreRepository;

	@Autowired
	private EventProducer eventProducer;

	@Override
	public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
		List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
		if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
			throw new ConcurrencyException();
		}

		int version = expectedVersion;
		for (BaseEvent event : events) {
			version++;
			event.setVersion(version);
			EventModel eventModel = EventModel.builder().timeStamp(new Date()).aggregateIdentifier(aggregateId)
					.aggregateType(SkillProfileAggregate.class.getTypeName()).version(version)
					.eventType(event.getClass().getTypeName()).eventData(event).build();
			EventModel persistedEvent = eventStoreRepository.save(eventModel);

			if (!persistedEvent.getId().isEmpty()) {
				eventProducer.produce(event.getClass().getSimpleName(), event);
			}
		}
	}

	@Override
	public List<BaseEvent> getEvents(String aggregateId) {
		List<EventModel> eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);

		if (eventStream == null || eventStream.isEmpty()) {
			throw new AggregateNotFoundException("Incorrect Account ID provided");
		}

		return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
	}

}
