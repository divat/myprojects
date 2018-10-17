package com.codemantra.maestro.exception;

public class CustomException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public CustomException(){
		super();
	}
	
	public CustomException(String msg) {
		super(msg);
	}
	
	public CustomException(String msg, Exception ex){
		super(msg, ex);
	}
}