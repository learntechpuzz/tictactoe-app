package com.wipro.tictactoe.utils;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicTacToe {

	private static final Logger logger = LoggerFactory.getLogger(TicTacToe.class);

	int[] rowCounter;
	int[] colCounter;
	int diagLeft;
	int diagRight;
	int size;
	int minValue;

	public TicTacToe(int n) {
		rowCounter = new int[n];
		colCounter = new int[n];
		diagLeft = 0;
		diagRight = 0;
		size = n;
		minValue = 0;
	}

	public TicTacToe(int[] rowCounter, int[] colCounter, int diagLeft, int diagRight, int size, int minValue) {
		super();
		this.rowCounter = rowCounter;
		this.colCounter = colCounter;
		this.diagLeft = diagLeft;
		this.diagRight = diagRight;
		this.size = size;
		this.minValue = minValue;
	}

	/**
	 * Player move
	 * 
	 * @param row
	 * @param col
	 * @param player (1 - human, -1 - machine)
	 * @return 0: No one wins 1: Player1 wins 2: Player2 wins
	 */
	public int move(int row, int col, int player) {
		logger.debug("move::player: " + player);
		logger.debug("move::row: " + row);
		logger.debug("move::col: " + col);

		int move = player == 1 ? 1 : -1;
		logger.debug("move::move: " + move);

		rowCounter[row] += move;
		colCounter[col] += move;
		if (row == col)
			diagLeft += move;
		if (row == size - col - 1)
			diagRight += move;

		logger.debug("move::rowCounter[" + row + "]: " + rowCounter[row]);
		logger.debug("move::colCounter[" + col + "]: " + colCounter[col]);
		logger.debug("move::diagLeft: " + diagLeft);
		logger.debug("move::diagRight: " + diagLeft);
		logger.debug("move::minValue: "
				+ CommonUtils.findMin(Arrays.asList(rowCounter[row], colCounter[col], diagLeft, diagRight)));
		minValue = CommonUtils.findMin(Arrays.asList(rowCounter[row], colCounter[col], diagLeft, diagRight));

		if (rowCounter[row] == size || colCounter[col] == size || diagLeft == size || diagRight == size)
			return Constants.PLAYER_WINS;
		else if ((rowCounter[row] == -size || colCounter[col] == -size || diagLeft == -size || diagRight == -size))
			return Constants.MACHINE_WINS;
		else
			return Constants.GAME_IN_PROGRESS;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	@Override
	public String toString() {
		return "TicTacToe [rowCounter=" + Arrays.toString(rowCounter) + ", colCounter=" + Arrays.toString(colCounter)
				+ ", diagLeft=" + diagLeft + ", diagRight=" + diagRight + ", size=" + size + ", minValue=" + minValue
				+ "]";
	}

}
