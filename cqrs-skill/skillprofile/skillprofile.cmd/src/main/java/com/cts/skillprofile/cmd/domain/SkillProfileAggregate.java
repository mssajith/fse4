package com.cts.skillprofile.cmd.domain;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.cts.skillprofile.cmd.api.commands.CreateSkillProfileCommand;
import com.cts.skillprofile.cmd.api.commands.UpdateSkillProfileCommand;
import com.cts.skillprofile.cmd.exception.SkillUpdationException;
import com.cts.skillprofile.common.events.SkillProfileCreatedEvent;
import com.cts.skillprofile.common.events.SkillProfileModifiedEvent;
import com.techbank.cqrs.core.domain.AggregateRoot;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SkillProfileAggregate extends AggregateRoot {
	private Boolean active;
	private double balance;
	private Date skillUpdatedDate;

	public double getBalance() {
		return this.balance;
	}
	
	public SkillProfileAggregate(CreateSkillProfileCommand command) {
		raisedEvent(SkillProfileCreatedEvent.builder().id(command.getId())
				.name(command.getName())
				.associateId(command.getAssociateId())
				.email(command.getEmail())
				.mobile(command.getMobile())
			.skillList(command.getSkillList())
				.skillCreatedDate(new Date())
				.skillUpdatedDate(new Date())
				.build());
	}

	public void apply(SkillProfileCreatedEvent event) {
		this.id = event.getId();
		this.active = true;
		this.skillUpdatedDate = event.getSkillUpdatedDate();
	}

	public void depositFunds(UpdateSkillProfileCommand command) {
		if (!this.active) {
			throw new IllegalStateException("Funds cannot be deposited into a closed account");
		}

		long diffInMillies = Math.abs(new Date().getTime() - this.skillUpdatedDate.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		
		if (diff < 10) {
			throw new SkillUpdationException("You cannot update the skill that is less than 10 days old");
		}
		
		raisedEvent(SkillProfileModifiedEvent.builder().id(this.id).skillList(command.getSkillProfile())
				.skillUpdatedDate(new Date()).build());
	}

	public void apply(SkillProfileModifiedEvent event) {
		this.id = event.getId();
		this.balance = event.getAmount();
	}

}
