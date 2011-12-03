package org.rhok.prototype.exceptions;

public class WrongCoordinateArrayException extends Exception {

	private static final long serialVersionUID = 7867834272748129772L;

	private String message;
	
	public WrongCoordinateArrayException(String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
