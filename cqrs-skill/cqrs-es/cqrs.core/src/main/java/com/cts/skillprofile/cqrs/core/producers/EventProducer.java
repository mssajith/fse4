package com.cts.skillprofile.cqrs.core.producers;

import com.cts.skillprofile.cqrs.core.events.BaseEvent;

public interface EventProducer {
	public void produce(String topic, BaseEvent event);
}
