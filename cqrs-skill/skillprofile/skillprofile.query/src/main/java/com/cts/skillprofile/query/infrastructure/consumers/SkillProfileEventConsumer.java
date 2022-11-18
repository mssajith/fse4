package com.cts.skillprofile.query.infrastructure.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.cts.skillprofile.common.events.SkillProfileCreatedEvent;
import com.cts.skillprofile.common.events.SkillProfileModifiedEvent;
import com.cts.skillprofile.query.infrastructure.handlers.EventHandler;

@Service
public class SkillProfileEventConsumer implements EventConsumer {

	@Autowired
	private EventHandler eventHandler;

	@KafkaListener(topics = "SkillProfileCreatedEvent", groupId = "${spring.kafka.consumer.group-id}")
	@Override
	public void consume(SkillProfileCreatedEvent event, Acknowledgment ack) {
		eventHandler.on(event);
		ack.acknowledge();
	}

	@KafkaListener(topics = "SkillProfileModifiedEvent", groupId = "${spring.kafka.consumer.group-id}")
	@Override
	public void consume(SkillProfileModifiedEvent event, Acknowledgment ack) {
		eventHandler.on(event);
		ack.acknowledge();
	}

}
