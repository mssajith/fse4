package com.cts.skillprofile.query.infrastructure.handlers;

import com.cts.skillprofile.common.events.SkillProfileCreatedEvent;
import com.cts.skillprofile.common.events.SkillProfileModifiedEvent;

public interface EventHandler {
	void on(SkillProfileCreatedEvent event);
	void on(SkillProfileModifiedEvent event);
}
