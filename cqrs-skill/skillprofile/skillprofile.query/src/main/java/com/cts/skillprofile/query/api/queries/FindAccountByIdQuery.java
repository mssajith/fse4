package com.cts.skillprofile.query.api.queries;


import com.cts.skillprofile.cqrs.core.queries.BaseQuery;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByIdQuery extends BaseQuery{
private String id;
}
