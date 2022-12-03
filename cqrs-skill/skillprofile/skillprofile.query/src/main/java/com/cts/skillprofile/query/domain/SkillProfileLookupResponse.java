package com.cts.skillprofile.query.domain;

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
public class SkillProfileLookupResponse extends BaseResponse {
	
	private List<SkillProfile> skillProfiles;

	public SkillProfileLookupResponse(String message) {
		super(message);
	}
}
