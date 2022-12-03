package com.cts.skillprofile.query;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import com.cts.skillprofile.cqrs.core.infrastructure.QueryDispatcher;
import com.cts.skillprofile.query.api.queries.FindAccountByIdQuery;
import com.cts.skillprofile.query.api.queries.FindAllAccountsQuery;
import com.cts.skillprofile.query.api.queries.FindSkillProfileByAssociateIdQuery;
import com.cts.skillprofile.query.api.queries.FindSkillProfileByNameQuery;
import com.cts.skillprofile.query.api.queries.FindSkillProfileBySkillNameQuery;
import com.cts.skillprofile.query.api.queries.QueryHandler;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class QueryApplication {

	@Autowired
	private QueryDispatcher queryDispatcher;
	@Autowired
	private QueryHandler queryHandler;
	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@PostConstruct
	public void registerHanders() {
		queryDispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler:: handle);
		queryDispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler:: handle);
		queryDispatcher.registerHandler(FindSkillProfileByNameQuery.class, queryHandler:: handle);
		queryDispatcher.registerHandler(FindSkillProfileByAssociateIdQuery.class, queryHandler:: handle);
		queryDispatcher.registerHandler(FindSkillProfileBySkillNameQuery.class, queryHandler:: handle);
	}
}
