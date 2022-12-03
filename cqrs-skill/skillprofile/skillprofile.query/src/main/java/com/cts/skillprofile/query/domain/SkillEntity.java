package com.cts.skillprofile.query.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cts.skillprofile.common.dto.SkillType;
import com.cts.skillprofile.cqrs.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SkillEntity extends BaseEntity {
	private String skillName;
	private Integer skillRating;
	private SkillType skillType;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long skillRatingId;
    

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id")
    @JsonIgnoreProperties("skillRatingList")
    private SkillProfile skillProfile;
}
