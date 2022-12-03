package com.cts.skillprofile.cqrs.core.infrastructure;

import java.util.List;

import com.cts.skillprofile.cqrs.core.domain.BaseEntity;
import com.cts.skillprofile.cqrs.core.queries.BaseQuery;
import com.cts.skillprofile.cqrs.core.queries.QueryHandlerMethod;

public interface QueryDispatcher {
<T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
<U extends BaseEntity> List<U> send(BaseQuery query);
}
