package com.wipro.tictactoe.exception;

public enum ErrorMessages {
	
	GAME_NOT_FOUND("Game not found"),
	PLAYER_ALREADY_EXISTS("Player already exists"),
	PLAYER_NOT_FOUND("Player not found"),
	MOVE_NOT_ALLOWED("Move not allowed"),
	INTERNAL_SERVER_ERROR("Something went wrong. Please repeat this operation later.");

	private String errorMessage;

	ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
