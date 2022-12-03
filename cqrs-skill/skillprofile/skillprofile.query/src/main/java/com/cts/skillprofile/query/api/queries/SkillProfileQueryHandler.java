package com.cts.skillprofile.query.api.queries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.skillprofile.cqrs.core.domain.BaseEntity;
import com.cts.skillprofile.query.domain.SkillEntity;
import com.cts.skillprofile.query.domain.SkillProfile;
import com.cts.skillprofile.query.domain.SkillProfileRepository;
import com.cts.skillprofile.query.domain.SkillRepository;

@Service
public class SkillProfileQueryHandler implements QueryHandler {
	@Autowired
	private SkillProfileRepository skillProfileRepository;
	@Autowired
	private SkillRepository skillRepository;


	public List<BaseEntity> handle(FindSkillProfileByNameQuery query) {
		List<BaseEntity> skillProfile = skillProfileRepository.findByNameStartsWith(query.getName());
		if (skillProfile.isEmpty()) {
			return null;
		}
//		List<BaseEntity> skillProfileList = new ArrayList<>();
//		skillProfileList.add(skillProfile.get());
		return skillProfile;
	}

	public List<BaseEntity> handle(FindSkillProfileBySkillNameQuery query) {
		List<SkillEntity> skillProfile = skillRepository.findBySkillName(query.getSkillName());

		if (skillProfile.isEmpty()) {
			return null;
		}

		List<BaseEntity> skillProfileList = skillProfile.stream().map(skill -> {
			return skill.getSkillProfile();
		}).collect(Collectors.toList());

		return skillProfileList;
	}

	public List<BaseEntity> handle(FindSkillProfileByAssociateIdQuery query) {
		List<BaseEntity> skillProfile = skillProfileRepository.findByAssociateId(query.getAssociateId());
		if (skillProfile.isEmpty()) {
			return null;
		}
		return skillProfile;
	}

	@Override
	public List<BaseEntity> handle(FindAllAccountsQuery query) {
		Iterable<SkillProfile> bankAccounts = skillProfileRepository.findAll();
		List<BaseEntity> bankAccountsList = new ArrayList<>();
		bankAccounts.forEach(bankAccountsList::add);
		return bankAccountsList;
	}

	@Override
	public List<BaseEntity> handle(FindAccountByIdQuery query) {
		Optional<SkillProfile> bankAccount = skillProfileRepository.findById(query.getId());
		if (bankAccount.isEmpty()) {
			return null;
		}
		List<BaseEntity> bankAccountsList = new ArrayList<>();
		bankAccountsList.add(bankAccount.get());
		return bankAccountsList;
	}

}
