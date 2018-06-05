package com.bytewheels.rentcar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DetailsNotFoundException  extends RuntimeException {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 983319715343850719L;

	public DetailsNotFoundException(String exception) {
	    super(exception);
	}

}