package com.cts.skillprofile.query.api.queries;

import com.techbank.cqrs.core.queries.BaseQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class FindSkillProfileByAssociateIdQuery extends BaseQuery{
private String associateId;
}
