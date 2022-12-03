package com.cts.skillprofile.common.domain;

import java.util.Date;
import java.util.List;

import com.cts.skillprofile.common.dto.BaseResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SkillProfileVO {
	private String id;
	private String name;
	private String associateId;
	private String email;
	private Long mobile;
	private Date skillCreatedDate;
	private Date skillUpdatedDate;
	
    private List<SkillVO> skillRatingList;
}
