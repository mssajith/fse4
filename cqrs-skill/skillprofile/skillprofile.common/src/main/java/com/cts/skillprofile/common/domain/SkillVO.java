package com.cts.skillprofile.common.domain;

import com.cts.skillprofile.common.dto.BaseResponse;
import com.cts.skillprofile.common.dto.SkillType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SkillVO {
	private String skillName;
	private Integer skillRating;
	private SkillType skillType;
    private long skillRatingId;
    
//    private SkillProfileVO skillProfile;
}
