package com.techbank.cqrs.core.producers;

import com.techbank.cqrs.core.events.BaseEvent;

public interface EventProducer {
	public void produce(String topic, BaseEvent event);
}
