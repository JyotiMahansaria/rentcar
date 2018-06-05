package com.bytewheels.rentcar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectDateFormatException  extends RuntimeException {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 983319715343850719L;

	public IncorrectDateFormatException(String exception) {
	    super(exception);
	}

}