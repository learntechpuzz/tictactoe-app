package com.wipro.tictactoe.exception;

public class PlayerAlreadyExistsException extends RuntimeException {

	public PlayerAlreadyExistsException(String message) {
		super(message);
	}

}