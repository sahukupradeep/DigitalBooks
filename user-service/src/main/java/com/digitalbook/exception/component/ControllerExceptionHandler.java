package com.digitalbook.exception.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.digitalbook.exception.InvalidRequestException;
import com.digitalbook.exception.RequestNotFounException;
import com.digitalbook.exception.RoleNotFoundException;
import com.digitalbook.payload.response.MessageResponse;

@RestControllerAdvice
public class ControllerExceptionHandler {

	Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler(InvalidRequestException.class)
	public String handleBadRequest(InvalidRequestException ex) {
		logger.error(ex.getMessage());
		return ex.getMessage();
	}

	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	@ExceptionHandler(RoleNotFoundException.class)
	public String handleNotFound(RoleNotFoundException rnf) {
		logger.error("Requested Role not found");
		return "Error : Requested Role not found!";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	@ExceptionHandler(RequestNotFounException.class)
	public String handleNotFound(RequestNotFounException rnf) {
		logger.error(rnf.getMessage());
		return rnf.getMessage();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String handleGeneralError(MethodArgumentNotValidException ex) {

		logger.error("Argument not valid " + ex);

		List<FieldError> errors = ex.getFieldErrors();
		Map<String, String> errorMap = new HashMap<>();
		errors.forEach(error -> {
			String field = error.getField();
			String msg = error.getDefaultMessage();
			errorMap.put(field, msg);

		});

		return ex.getMessage();
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
	@ExceptionHandler(AccessDeniedException.class)
	public String handleGeneralError(AccessDeniedException ex) {

		logger.error("Error :" + ex);
		return "Error : " + ex.getMessage();
	}

	@ResponseStatus(HttpStatus.FORBIDDEN) // 403
	@ExceptionHandler(BadCredentialsException.class)
	public String handleGeneralError(BadCredentialsException ex) {

		logger.error("Error :" + ex);
		return "Error : " + ex.getMessage();
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(Exception.class)
	public String handleGeneralError(Exception ex) {

		logger.error("An error occurred procesing request" + ex);
		return "Error : An error occurred procesing request " + ex.getMessage();
	}
}
