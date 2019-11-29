package com.wipro.tictactoe.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicTacToe {

	private static final Logger logger = LoggerFactory.getLogger(TicTacToe.class);

	int[] rowCounter;
	int[] colCounter;
	int diagLeft;
	int diagRight;
	int size;
	Map<Integer, Integer> minValue;

	public TicTacToe(int n) {
		rowCounter = new int[n];
		colCounter = new int[n];
		diagLeft = 0;
		diagRight = 0;
		size = n;
		minValue = new HashMap<>();
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
		minValue.put(move, CommonUtils.findMin(Arrays.asList(rowCounter[row], colCounter[col], diagLeft, diagRight)));

		if (rowCounter[row] == size || colCounter[col] == size || diagLeft == size || diagRight == size)
			return Constants.PLAYER_WINS;
		else if ((rowCounter[row] == -size || colCounter[col] == -size || diagLeft == -size || diagRight == -size))
			return Constants.MACHINE_WINS;
		else
			return Constants.NO_ONE_WINS;
	}

	public Map<Integer, Integer> getMinValue() {
		return minValue;
	}

}
