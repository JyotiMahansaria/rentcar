package com.bytewheels.rentcar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectRequestException  extends RuntimeException {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 983319715343850719L;

	public IncorrectRequestException(String exception) {
	    super(exception);
	}

}