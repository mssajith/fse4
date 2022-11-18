package com.cts.skillprofile.cmd.api.commands;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.skillprofile.cmd.domain.SkillProfileAggregate;
import com.techbank.cqrs.core.handlers.EventSourcingHandler;

@Service
public class SkillProfileCommandHandler implements CommandHandler {
	@Autowired
	private EventSourcingHandler<SkillProfileAggregate> eventSourcingHandler;

	@Override
	public void handle(CreateSkillProfileCommand command) {
		var aggregate = new SkillProfileAggregate(command);
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(UpdateSkillProfileCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getId());
		command.setSkillUpdatedDate(new Date());
		aggregate.depositFunds(command);
		eventSourcingHandler.save(aggregate);
	}

	/**
	@Override
	public void handle(WithdrawFundsCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getId());
		
		if (command.getAmount() > aggregate.getBalance()) {
			throw new IllegalStateException("Withdrawal declined, Insufficient balance!");
		}
		aggregate.withdrawFunds(command.getAmount());
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(CloseAccountCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getId());
		aggregate.closeAccount();
		eventSourcingHandler.save(aggregate);
	}
**/
}