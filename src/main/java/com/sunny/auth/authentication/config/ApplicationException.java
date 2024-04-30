package com.sunny.auth.authentication.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApplicationException extends RuntimeException {
	
	private static final long serialVersionUID = -5431592282330927438L;

	public ApplicationException(String s) {  
		  super(s);  
	}  

}
