package com.cts.skillprofile.query.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.cts.skillprofile.common.dto.AccountType;
import com.cts.skillprofile.common.dto.Skill;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techbank.cqrs.core.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SkillProfile extends BaseEntity {
	@Id
	private String id;
	private String name;
	private String associateId;
	private String email;
	private Long mobile;
	private Date skillCreatedDate;
	private Date skillUpdatedDate;
	
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "skillProfile", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("skillProfile")
    private List<SkillEntity> skillRatingList;
}
