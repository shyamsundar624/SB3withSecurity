package com.shyam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.shyam.entity.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateProductException.class)
	public ResponseEntity<ErrorResponse> DuplicateProductExceptionHandler(DuplicateProductException dpex,
			WebRequest request) {
		ErrorResponse response = new ErrorResponse(dpex.getMessage(), request.getDescription(false),
				"Duplicate Product Fount");
		return new ResponseEntity(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> productNotFoundEx(ProductNotFoundException pnfex, WebRequest request) {
		ErrorResponse response = new ErrorResponse(pnfex.getMessage(), request.getDescription(false),
				"Product Not Found Exception");
		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
	}
}
