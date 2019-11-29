package com.wipro.tictactoe.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wipro.tictactoe.model.Move;

@Component
public class TicTacToeUtils {

	private static final Logger logger = LoggerFactory.getLogger(TicTacToeUtils.class);

	/**
	 * Check game status
	 * 
	 * @param moves
	 * @return status 0 - No one wins, 1 - Player wins, 2 - Machine wins
	 */
	public int checkGameStatus(List<Move> moves) {
		int gameStatus = Constants.NO_ONE_WINS;
		if (moves == null)
			return gameStatus;
		if (moves != null && moves.size() > (Constants.GAME_SIZE + 1)) { // check if min moves to win game reached
			return gameStatus;
		}
		TicTacToe ticTacToe = new TicTacToe(Constants.GAME_SIZE);
		for (Move move : moves) {
			gameStatus = ticTacToe.move(getRow(move.getMove()), getCol(move.getMove()), move.getPlayer());
			if (gameStatus != Constants.NO_ONE_WINS) // return game status if player or machine wins
				return gameStatus;
		}
		return gameStatus;

	}

	public int findBestMove(List<Move> moves) {
		int bestMove = -1;
		int gameStatus = Constants.NO_ONE_WINS;
		TicTacToe ticTacToe = new TicTacToe(Constants.GAME_SIZE);
		List<Integer> m = new ArrayList<>();

		if (moves != null && moves.size() > 0) {
			for (Move move : moves) {
				m.add(move.getMove());
			}
		}

		for (int i = 0; i < Constants.GAME_SIZE; i++) {
			if (!m.contains(i)) {
				gameStatus = ticTacToe.move(getRow(i), getCol(i), Constants.PLAYER);
				if (gameStatus == Constants.PLAYER_WINS) {
					bestMove = i;
					return bestMove;
				}
			}
		}

		for (int i = 0; i < Constants.GAME_SIZE; i++) {
			if (!m.contains(i)) {
				gameStatus = ticTacToe.move(getRow(i), getCol(i), Constants.MACHINE);
				if (gameStatus == Constants.MACHINE_WINS) {
					bestMove = i;
					return bestMove;
				}
			}
		}

		int minValue = CommonUtils.findMin(ticTacToe.getMinValue().values());
		Set<Integer> bestMoves = CommonUtils.getKeysByValue(ticTacToe.getMinValue(), minValue);
		bestMove = bestMoves.stream().findAny().get();

		logger.debug("bestMove: " + bestMove);
		return bestMove;
	}

	/**
	 * Get row
	 * 
	 * @param move
	 * @return
	 */
	public int getRow(int move) {
		return (move / Constants.GAME_SIZE);
	}

	/**
	 * Get col
	 * 
	 * @param move
	 * @return
	 */
	public int getCol(int move) {
		return (move % Constants.GAME_SIZE);
	}

}
