package com.ims.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ims.dto.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleAllException(Exception ex) {
		Response response = Response.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage())
				.build();
		
		return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NotFoundExcepption.class)
	public ResponseEntity<Response> handleNotFoundException(NotFoundExcepption ex) {
		Response response = Response.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NameValueRequiredExcepption.class)
	public ResponseEntity<Response> handleNameValueRequiredExcepptionException(NameValueRequiredExcepption ex) {
		Response response = Response.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidCredentialsExcepption.class)
	public ResponseEntity<Response> handleInvalidCredentialsExcepption(InvalidCredentialsExcepption ex) {
		Response response = Response.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(ex.getMessage())
				.build();
		
		return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
	}
	
}
