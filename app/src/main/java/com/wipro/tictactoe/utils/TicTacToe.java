package com.wipro.tictactoe.utils;

public class TicTacToe {

	int[] rowCounter;
	int[] colCounter;
	int diagLeft;
	int diagRight;
	int length;

	/** Initialize with size n */
	public TicTacToe(int n) {
		rowCounter = new int[n];
		colCounter = new int[n];
		diagLeft = 0;
		diagRight = 0;
		length = n;

	}

	/**
	 * Player {player} make a move at ({row}, {col})
	 * 
	 * @param row
	 * @param col
	 * @param player
	 * @return 0: No one wins 1: Player1 wins 2: Player2 wins
	 */
	public int move(int row, int col, int player) {
		int move = player == 1 ? 1 : -1;
		rowCounter[row] += move;
		colCounter[col] += move;
		if (row == col)
			diagLeft += move;
		if (row == length - col - 1)
			diagRight += move;
		if (rowCounter[row] == length || colCounter[col] == length || diagLeft == length || diagRight == length)
			return 1;
		else if ((rowCounter[row] == -length || colCounter[col] == -length || diagLeft == -length
				|| diagRight == -length))
			return 2;
		else
			return 0;
	}

}
