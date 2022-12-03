package com.cts.skillprofile.cmd.infrastructure;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.skillprofile.cmd.domain.SkillProfileAggregate;
import com.cts.skillprofile.cqrs.core.domain.AggregateRoot;
import com.cts.skillprofile.cqrs.core.events.BaseEvent;
import com.cts.skillprofile.cqrs.core.handlers.EventSourcingHandler;
import com.cts.skillprofile.cqrs.core.infrastructure.EventStore;

@Service
public class SkillProfileEventSourcingHandler implements EventSourcingHandler<SkillProfileAggregate>{
	@Autowired
	private EventStore eventStore;
	
	@Override
	public void save(AggregateRoot aggregate) {
		eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
		aggregate.markChangesAsCommitted();
		
	}

	@Override
	public SkillProfileAggregate getById(String id) {
		SkillProfileAggregate aggregate = new SkillProfileAggregate();
		List<BaseEvent> events = eventStore.getEvents(id);
		
		if (events != null && !events.isEmpty()) {
			aggregate.replayEvents(events);
			Optional<Integer> latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
			aggregate.setVersion(latestVersion.get());
		}
		
		return aggregate;
	}

}
