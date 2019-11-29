package com.wipro.tictactoe.exception;

public class GameNotFoundException extends RuntimeException {

	public GameNotFoundException(String message) {
		super(message);
	}

}
