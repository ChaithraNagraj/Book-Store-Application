package com.bridgelabz.bookstore.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.bookstore.response.Response;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Response customErrorDetails = new Response(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				ex.getMessage());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Response customErrorDetails = new Response(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				ex.getMessage());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.METHOD_NOT_ALLOWED);

	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> handleUserException(UserException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Response> handleUserNotFoundException(UserNotFoundException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(TokenNotFoundException.class)
	public ResponseEntity<Response> handleTokenNotFoundException(TokenNotFoundException ex) {
		Response customErrorDetails = new Response(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage());

		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);

	}
}