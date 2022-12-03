package com.cts.skillprofile.query.api.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.skillprofile.common.domain.SkillProfileVO;
import com.cts.skillprofile.common.domain.SkillVO;
import com.cts.skillprofile.common.dto.SkillType;
import com.cts.skillprofile.query.domain.SkillEntity;
import com.cts.skillprofile.query.domain.SkillProfile;

@Service
public class SkillProfileResponseMapper {
	public List<SkillProfileVO> mapServiceResponse(List<SkillProfile> skillProfileEntities) {
		List<SkillProfileVO> skillProfiles = new ArrayList<>();
		
		for (SkillProfile skillProfileEntity : skillProfileEntities) {
			SkillProfileVO skillProfileVo  = new SkillProfileVO();
			skillProfileVo.setId(skillProfileEntity.getId());
			skillProfileVo.setName(skillProfileEntity.getName());
			skillProfileVo.setAssociateId(skillProfileEntity.getAssociateId());
			skillProfileVo.setEmail(skillProfileEntity.getEmail());
			skillProfileVo.setMobile(skillProfileEntity.getMobile());
			skillProfileVo.setSkillCreatedDate(skillProfileEntity.getSkillCreatedDate());
			skillProfileVo.setSkillUpdatedDate(skillProfileEntity.getSkillUpdatedDate());
			skillProfileVo.setSkillRatingList(mapSkillVo(skillProfileEntity.getSkillRatingList()));
			skillProfiles.add(skillProfileVo);
		}
		
		return skillProfiles;
	}
	
	private List<SkillVO> mapSkillVo(List<SkillEntity> skillEntities){
		List<SkillVO> skillList = new ArrayList<>();
		
		for (SkillEntity skillEntity : skillEntities) {
			SkillVO skillVo = new SkillVO();
			skillVo.setSkillName(skillEntity.getSkillName());
			skillVo.setSkillRating(skillEntity.getSkillRating());
			skillVo.setSkillType(skillEntity.getSkillType());
			skillVo.setSkillRatingId(skillEntity.getSkillRatingId());
			skillList.add(skillVo);
		}
		return skillList;
	}
}
