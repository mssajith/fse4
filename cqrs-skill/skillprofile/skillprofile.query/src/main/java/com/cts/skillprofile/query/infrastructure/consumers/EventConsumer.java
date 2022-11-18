package com.cts.skillprofile.query.infrastructure.consumers;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

import com.cts.skillprofile.common.events.SkillProfileCreatedEvent;
import com.cts.skillprofile.common.events.SkillProfileModifiedEvent;

public interface EventConsumer {
	void consume(@Payload SkillProfileCreatedEvent event, Acknowledgment ack);

	void consume(@Payload SkillProfileModifiedEvent event, Acknowledgment ack);
}
