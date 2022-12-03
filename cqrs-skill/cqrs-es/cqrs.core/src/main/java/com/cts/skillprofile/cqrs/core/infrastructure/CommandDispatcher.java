package com.cts.skillprofile.cqrs.core.infrastructure;

import com.cts.skillprofile.cqrs.core.commands.BaseCommand;
import com.cts.skillprofile.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
<T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
void send(BaseCommand command);
}
