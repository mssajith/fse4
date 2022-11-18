package com.cts.skillprofile.cmd.api.dto;

import com.cts.skillprofile.common.dto.BaseResponse;

public class CreateSkillProfileResponse extends BaseResponse {
	private String id;

	public CreateSkillProfileResponse(String message, String id) {
		super(message);
		this.id = id;
	}
}
