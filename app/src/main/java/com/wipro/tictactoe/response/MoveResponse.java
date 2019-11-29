package com.wipro.tictactoe.response;

import com.wipro.tictactoe.model.Move;

public class MoveResponse {

	private Move move;
	private int status;

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
