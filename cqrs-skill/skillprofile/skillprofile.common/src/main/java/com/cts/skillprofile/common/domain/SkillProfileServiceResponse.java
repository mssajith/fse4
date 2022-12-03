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
public class SkillProfileServiceResponse extends BaseResponse {
	private List<SkillProfileVO> skillProfiles;
	
	public SkillProfileServiceResponse(String message) {
		super(message);
	}
}
