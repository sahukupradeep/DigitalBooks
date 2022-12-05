package com.digitalbook.exception.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.digitalbook.exception.RoleNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	Logger log=LoggerFactory.getLogger(ControllerExceptionHandler.class);
	
	
	/*@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	@ExceptionHandler(AccountNotFoundException.class)
	public void handleNotFound(AccountNotFoundException ex) {
		log.error("Requested account not found");
	}*/

	/*@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler(InvalidAccountRequestException.class)
	public void handleBadRequest(InvalidAccountRequestException ex) {
		log.error("Invalid account supplied in request");
	}*/
	
	/*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(Exception.class)
	public void handleGeneralError(Exception ex) {
		log.error("An error occurred procesing request" + ex);
	}*/
	
	
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	@ExceptionHandler(RoleNotFoundException.class)
	public String handleNotFound(RoleNotFoundException rnf) {
		log.error("Requested Role not found");
		return "Requested Role not found";
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(Exception.class)
	public String handleGeneralError(Exception ex) {
		log.error("An error occurred procesing request" + ex);
		return "An error occurred procesing request "+ex.getMessage();
	}
}
