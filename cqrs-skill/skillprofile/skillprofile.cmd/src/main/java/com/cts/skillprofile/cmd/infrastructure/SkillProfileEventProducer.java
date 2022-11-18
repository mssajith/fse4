package com.cts.skillprofile.cmd.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.producers.EventProducer;

@Service
public class SkillProfileEventProducer implements EventProducer{
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	@Override
	public void produce(String topic, BaseEvent event) {
		this.kafkaTemplate.send(topic, event);		
	}

}
