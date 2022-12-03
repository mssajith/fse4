package com.cts.skillprofile.cqrs.core.commands;

import com.cts.skillprofile.cqrs.core.messages.Message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class BaseCommand extends Message{
	public  BaseCommand(String id) {
		super(id);
	}

}
