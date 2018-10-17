package com.cm.style.profile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StylesNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public StylesNotFoundException(String exception){
		super(exception);
	}
}
