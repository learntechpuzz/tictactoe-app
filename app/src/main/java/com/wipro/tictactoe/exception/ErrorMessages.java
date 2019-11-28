package com.wipro.tictactoe.exception;

public enum ErrorMessages {
	
	PLAYER_ALREADY_EXISTS("Player already exists"),
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
