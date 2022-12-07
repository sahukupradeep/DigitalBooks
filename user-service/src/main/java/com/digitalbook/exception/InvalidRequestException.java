package com.digitalbook.exception;

public class InvalidRequestException extends RuntimeException {

	private static final long serialVersionUID = 2468434988680850339L;

	public InvalidRequestException(String msg) {
		super(msg);
	}

}
