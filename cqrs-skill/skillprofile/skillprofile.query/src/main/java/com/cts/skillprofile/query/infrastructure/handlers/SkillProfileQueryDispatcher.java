package com.cts.skillprofile.query.infrastructure.handlers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cts.skillprofile.cqrs.core.domain.BaseEntity;
import com.cts.skillprofile.cqrs.core.infrastructure.QueryDispatcher;
import com.cts.skillprofile.cqrs.core.queries.BaseQuery;
import com.cts.skillprofile.cqrs.core.queries.QueryHandlerMethod;


@Service
public class SkillProfileQueryDispatcher implements QueryDispatcher{
private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();
	@Override
	public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
		List<QueryHandlerMethod> handlers =  routes.computeIfAbsent(type, c -> new LinkedList<>());
		handlers.add(handler);
	}

	@Override
	public <U extends BaseEntity> List<U> send(BaseQuery query) {
		List<QueryHandlerMethod> handlers = routes.get(query.getClass());
		if (handlers == null || handlers.size() <= 0) {
			throw new RuntimeException("No Query Handler was registered");
		}
		if (handlers.size() > 1) {
			throw new RuntimeException("Cannot send query to more than one handler");
		}
		return handlers.get(0).handle(query);
	}

}
