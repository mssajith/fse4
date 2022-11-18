package com.cts.skillprofile.query.api.controllers;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.skillprofile.query.api.dto.EqualityType;
import com.cts.skillprofile.query.api.dto.SkillProfileLookupResponse;
import com.cts.skillprofile.query.api.queries.FindAccountByIdQuery;
import com.cts.skillprofile.query.api.queries.FindAllAccountsQuery;
import com.cts.skillprofile.query.api.queries.FindSkillProfileByAssociateIdQuery;
import com.cts.skillprofile.query.api.queries.FindSkillProfileByNameQuery;
import com.cts.skillprofile.query.api.queries.FindSkillProfileBySkillNameQuery;
import com.cts.skillprofile.query.domain.SkillProfile;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "/api/v1/admin")
public class SkillProfileLookupController {
	private Logger logger = Logger.getLogger(SkillProfileLookupController.class.getName());
	@Autowired
	private QueryDispatcher queryDispatcher;

	@GetMapping(path = "/")
	public ResponseEntity<SkillProfileLookupResponse> getAllAccounts() {
		try {
			List<SkillProfile> accounts = queryDispatcher.send(new FindAllAccountsQuery());
			if (accounts == null || accounts.size() == 0) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			SkillProfileLookupResponse response = SkillProfileLookupResponse.builder().skillProfiles(accounts)
					.message(MessageFormat.format("Successfully returned {0} bank accounts(s)", accounts.size()))
					.build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			String safeErrorMsg = "Failed to complete get all accounts request";
			logger.log(Level.SEVERE, safeErrorMsg, e);
			return new ResponseEntity<SkillProfileLookupResponse>(new SkillProfileLookupResponse(safeErrorMsg),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{criteria}/{criteriaValue}")
	public ResponseEntity<SkillProfileLookupResponse> getAccounByCriteria(@PathVariable(value = "criteria") String criteria,
			@PathVariable(value = "criteriaValue") String criteriaValue) {
		try {
			List<SkillProfile> skillProfiles = null;
			switch (criteria) {
			case "byId":
				skillProfiles = queryDispatcher.send(new FindAccountByIdQuery(criteriaValue));
				break;
			case "byName":
				skillProfiles = queryDispatcher.send(new FindSkillProfileByNameQuery(criteriaValue));
				break;
			case "bySkillName":
				skillProfiles = queryDispatcher.send(new FindSkillProfileBySkillNameQuery(criteriaValue));
				break;
			case "byAssociateId":
				skillProfiles = queryDispatcher.send(new FindSkillProfileByAssociateIdQuery(criteriaValue));
				break;
			}
			
			if (skillProfiles == null || skillProfiles.size() == 0) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			SkillProfileLookupResponse response = SkillProfileLookupResponse.builder().skillProfiles(skillProfiles)
					.message("Successfully returned Skill Profiles").build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			String safeErrorMsg = "Failed to complete skill profile request with criteria " + criteria;
			logger.log(Level.SEVERE, safeErrorMsg, e);
			return new ResponseEntity<SkillProfileLookupResponse>(new SkillProfileLookupResponse(safeErrorMsg),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
	@GetMapping(path = "/byId/{id}")
	public ResponseEntity<AccountLookupResponse> getAccounById(@PathVariable(value = "id") String id) {
		try {
			List<SkillProfile> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
			if (accounts == null || accounts.size() == 0) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			AccountLookupResponse response = AccountLookupResponse.builder().skillProfiles(accounts)
					.message("Successfully returned bank account").build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			String safeErrorMsg = "Failed to complete get all accounts By ID request";
			logger.log(Level.SEVERE, safeErrorMsg, e);
			return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMsg),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/byHolder/{accountHolder}")
	public ResponseEntity<AccountLookupResponse> getAccounByHolder(
			@PathVariable(value = "accountHolder") String accountHolder) {
		try {
			List<SkillProfile> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
			if (accounts == null || accounts.size() == 0) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			AccountLookupResponse response = AccountLookupResponse.builder().skillProfiles(accounts)
					.message("Successfully returned bank account").build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			String safeErrorMsg = "Failed to complete get all accounts By Holder request";
			logger.log(Level.SEVERE, safeErrorMsg, e);
			return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMsg),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/byName/{name}")
	public ResponseEntity<AccountLookupResponse> getSkillProfileByName(
			@PathVariable(value = "name") String name) {
		try {
			List<SkillProfile> skillProfiles = queryDispatcher.send(new FindSkillProfileByNameQuery(name));
			if (skillProfiles == null || skillProfiles.size() == 0) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			AccountLookupResponse response = AccountLookupResponse.builder().skillProfiles(skillProfiles)
					.message("Successfully returned Skill Profile").build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			String safeErrorMsg = "Failed to complete get all skill Profiles By Name request";
			logger.log(Level.SEVERE, safeErrorMsg, e);
			return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMsg),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/bySkillName/{skillName}")
	public ResponseEntity<AccountLookupResponse> getSkillProfileBySkillName(
			@PathVariable(value = "skillName") String skillName) {
		try {
			List<SkillProfile> skillProfiles = queryDispatcher.send(new FindSkillProfileBySkillNameQuery(skillName));
			if (skillProfiles == null || skillProfiles.size() == 0) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			AccountLookupResponse response = AccountLookupResponse.builder().skillProfiles(skillProfiles)
					.message("Successfully returned Skill Profile").build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			String safeErrorMsg = "Failed to complete get all skill Profiles By Name request";
			logger.log(Level.SEVERE, safeErrorMsg, e);
			return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMsg),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/byAssociateId/{associateId}")
	public ResponseEntity<AccountLookupResponse> getSkillProfileByAssociateId(
			@PathVariable(value = "associateId") String associateId) {
		try {
			List<SkillProfile> skillProfiles = queryDispatcher.send(new FindSkillProfileByAssociateIdQuery(associateId));
			if (skillProfiles == null || skillProfiles.size() == 0) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			AccountLookupResponse response = AccountLookupResponse.builder().skillProfiles(skillProfiles)
					.message("Successfully returned Skill Profile").build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			String safeErrorMsg = "Failed to complete get all skill Profiles By Name request";
			logger.log(Level.SEVERE, safeErrorMsg, e);
			return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMsg),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/withBalance/{equalityType}/{balance}")
	public ResponseEntity<AccountLookupResponse> getAccountWithBalance(
			@PathVariable(value = "equalityType") EqualityType equalityType,
			@PathVariable(value = "balance") double balance) {
		try {
			List<SkillProfile> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(equalityType,balance));
			if (accounts == null || accounts.size() == 0) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			AccountLookupResponse response = AccountLookupResponse.builder().skillProfiles(accounts)
					.message(MessageFormat.format("Successfully returned {0} bank accounts(s)", accounts.size()))
					.build();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			String safeErrorMsg = "Failed to complete get all accounts with Balance request";
			logger.log(Level.SEVERE, safeErrorMsg, e);
			return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMsg),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
**/
}
