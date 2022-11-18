package com.cts.skillprofile.cmd.api.commands;


public interface CommandHandler {
	void handle(CreateSkillProfileCommand command);

	void handle(UpdateSkillProfileCommand command);

//	void handle(WithdrawFundsCommand command);

//	void handle(CloseAccountCommand command);
}
