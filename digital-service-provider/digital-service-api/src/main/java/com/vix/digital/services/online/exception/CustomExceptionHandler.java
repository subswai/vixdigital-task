package com.vix.digital.services.online.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

import static org.springframework.http.HttpStatus.*;

/**
 * Handles custom exception
 * @author Subhasis Swain
 *
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
		return new ResponseEntity(createResponse(ex, INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ServiceNotFoundException.class)
	@ResponseStatus(NOT_FOUND)
	public final ResponseEntity<Object> handleServiceNotFoundException(ServiceNotFoundException ex) {
		return new ResponseEntity(createResponse(ex, NOT_FOUND), NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers, HttpStatus status,
																  WebRequest request) {
		return new ResponseEntity(createResponse(ex, BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	private ExceptionResponse createResponse(Exception ex, HttpStatus status) {
		return new ExceptionResponse(new Date(), status.value(), ex.getMessage());
	}
}
