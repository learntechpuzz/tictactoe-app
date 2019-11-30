package com.wipro.tictactoe.response;

import com.wipro.tictactoe.model.Move;
import com.wipro.tictactoe.utils.Constants;

public class MoveResponse {

	private Move move;
	private int status = Constants.GAME_IN_PROGRESS;

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
