package com.cts.skillprofile.query.infrastructure.handlers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.skillprofile.common.dto.Skill;
import com.cts.skillprofile.common.events.SkillProfileCreatedEvent;
import com.cts.skillprofile.common.events.SkillProfileModifiedEvent;
import com.cts.skillprofile.query.domain.SkillEntity;
import com.cts.skillprofile.query.domain.SkillProfile;
import com.cts.skillprofile.query.domain.SkillProfileRepository;

@Service
public class SkillProfileEventHandler implements EventHandler {
	@Autowired
	SkillProfileRepository accountRepository;

	@Override
	public void on(SkillProfileCreatedEvent event) {
		var bankAccount = SkillProfile.builder().id(event.getId())
				.name(event.getName())
				.associateId(event.getAssociateId())
				.email(event.getEmail())
				.mobile(event.getMobile())
				.skillCreatedDate(event.getSkillCreatedDate())
				.skillUpdatedDate(event.getSkillUpdatedDate())
				.build();
		
		List<SkillEntity> skillRatingList = event.getSkillList().stream()
				.map(skill -> {
					SkillEntity skillEntity = new SkillEntity(skill.getSkillName(), skill.getSkillRating(), skill.getSkillType(), 0l, null);
					skillEntity.setSkillProfile(bankAccount);
				return skillEntity;
				})
				.collect(Collectors.toList());
		bankAccount.setSkillRatingList(skillRatingList);

		accountRepository.save(bankAccount);
	}

	@Override
	public void on(SkillProfileModifiedEvent event) {
		var bankAccount = accountRepository.findById(event.getId());
		if (bankAccount.isEmpty()) {
			return;
		}
		
		SkillProfile skillProfile = bankAccount.get();
				
		List<SkillEntity> skillList = skillProfile.getSkillRatingList();
		List<Skill> skillsNew = event.getSkillList();
		
		skillList.stream().forEach(skill -> {
			skillsNew.stream().forEach(skillNew -> {
				if (skill.getSkillName().equalsIgnoreCase(skillNew.getSkillName())){
					skill.setSkillRating(skillNew.getSkillRating());
				}
			});
		});
		skillProfile.setSkillUpdatedDate(new Date());
		/*
		var currentBalance = bankAccount.get().getBalance();
		var latestBalance = currentBalance + event.getAmount();
		bankAccount.get().setBalance(latestBalance);
		*/
//		accountRepository.save(bankAccount.get());
		accountRepository.save(skillProfile);
	}
}
