package com.wipro.tictactoe.utils;

public class TicTacToe {

	int[] rowCounter;
	int[] colCounter;
	int diagLeft;
	int diagRight;
	int length;

	/** Initialize */
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

	public static void main(String[] args) {
		TicTacToe obj = new TicTacToe(3);
		int move1 = obj.move(0, 0, 1);
		System.out.println("move1: " + move1);
		int move2 = obj.move(0, 1, 2);
		System.out.println("move2: " + move2);
		int move3 = obj.move(0, 2, 1);
		System.out.println("move3: " + move3);
		int move4 = obj.move(2, 1, 2);
		System.out.println("move4: " + move4);		
		int move5 = obj.move(2, 2, 1);
		System.out.println("move5: " + move5);
		int move6 = obj.move(1, 0, 2);
		System.out.println("move6: " + move6);
		int move7 = obj.move(1, 1, 1);
		System.out.println("move7: " + move7);
		int move8 = obj.move(2, 0, 1);
		System.out.println("move8: " + move8);
		
	}
}
