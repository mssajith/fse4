package com.cts.skillprofile.cmd.infrastructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cts.skillprofile.cqrs.core.commands.BaseCommand;
import com.cts.skillprofile.cqrs.core.commands.CommandHandlerMethod;
import com.cts.skillprofile.cqrs.core.infrastructure.CommandDispatcher;

@Service
public class SkillProfileCommandDispatcher implements CommandDispatcher{
private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();
	@Override
	public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
		var handlers  = routes.computeIfAbsent(type, c -> new LinkedList<>());
		handlers.add(handler);
	}

	@Override
	public void send(BaseCommand command) {
		List<CommandHandlerMethod> route = routes.get(command.getClass());
		if (route == null || route.size() == 0) {
			throw new RuntimeException("No Command Handlers are Registered");
		}
		if (route.size() > 1) {
			throw new RuntimeException("Cannot send command to more than one handler");
		}
		route.get(0).handle(command);
	}

}
