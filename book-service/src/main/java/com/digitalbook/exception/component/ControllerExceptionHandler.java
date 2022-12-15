package com.digitalbook.exception.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.digitalbook.exception.InvalidRequestException;
import com.digitalbook.exception.RequestNotFounException;
import com.digitalbook.payload.response.MessageResponse;

@RestControllerAdvice
public class ControllerExceptionHandler {

	Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler(InvalidRequestException.class)
	public MessageResponse handleBadRequest(InvalidRequestException ex) {
		logger.error(ex.getMessage());
		return new MessageResponse(ex.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	@ExceptionHandler(RequestNotFounException.class)
	public MessageResponse handleNotFound(RequestNotFounException rnf) {
		logger.error(rnf.getMessage());
		return new MessageResponse(rnf.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public MessageResponse handleGeneralError(MethodArgumentNotValidException ex) {

		logger.error("Error : Argument not valid " + ex);

		List<FieldError> errors = ex.getFieldErrors();
		Map<String, String> errorMap = new HashMap<>();
		errors.forEach(error -> {
			String field = error.getField();
			String msg = error.getDefaultMessage();
			errorMap.put(field, msg);

		});

		return new MessageResponse(ex.getMessage());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(Exception.class)
	public MessageResponse handleGeneralError(Exception ex) {

		logger.error("Error : An error occurred procesing request" + ex);
		return new MessageResponse("Error : An error occurred procesing request " + ex.getMessage());
	}
}
