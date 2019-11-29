package com.wipro.tictactoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wipro.tictactoe.response.ApiError;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(PlayerAlreadyExistsException.class)
	public ResponseEntity<Object> handlePlayerAlreadyExistsException(PlayerAlreadyExistsException ex) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
		return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MoveNotAllowedException.class)
	public ResponseEntity<Object> handleMoveNotAllowedException(MoveNotAllowedException ex) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
		return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
