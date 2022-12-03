package com.cts.skillprofile.cqrs.core.exceptions;

public class AggregateNotFoundException extends RuntimeException {

	public AggregateNotFoundException(String message) {
		super(message);
	}
}
