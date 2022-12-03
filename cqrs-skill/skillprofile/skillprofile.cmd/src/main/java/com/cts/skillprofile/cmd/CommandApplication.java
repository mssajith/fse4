package com.cts.skillprofile.cmd;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.cts.skillprofile.cmd.api.commands.CommandHandler;
import com.cts.skillprofile.cmd.api.commands.CreateSkillProfileCommand;
import com.cts.skillprofile.cmd.api.commands.UpdateSkillProfileCommand;
import com.cts.skillprofile.cqrs.core.infrastructure.CommandDispatcher;

@SpringBootApplication
@EnableMongoRepositories
public class CommandApplication {
	@Autowired
	private CommandDispatcher commandDispatcher;
	
	@Autowired
	private CommandHandler commandHandler;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers() {
		commandDispatcher.registerHandler(CreateSkillProfileCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(UpdateSkillProfileCommand.class, commandHandler::handle);
	}
}
