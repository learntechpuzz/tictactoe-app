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
		logger.debug("gameStatus: " + gameStatus);
		return gameStatus;

	}

	public int findBestMove(List<Move> moves) {
		logger.debug("findBestMove::moves: " + moves.toString());
		int bestMove = -1;
		int gameStatus = Constants.NO_ONE_WINS;
		TicTacToe ticTacToe = null;

		List<Integer> m = new ArrayList<>();

		if (moves != null && moves.size() > 0) {
			for (Move move : moves) {
				m.add(move.getMove());
			}
		}
		logger.debug("findBestMove::m: " + m.toString());

		ticTacToe = new TicTacToe(Constants.GAME_SIZE);
		for (int i = 0; i < Math.pow(2, Constants.GAME_SIZE); i++) {
			if (!m.contains(i)) {
				logger.debug("findBestMove::player::i: " + i);
				logger.debug("findBestMove::player::getRow("+i+"): " + getRow(i));
				logger.debug("findBestMove::player::getCol("+i+"): " + getCol(i));
				gameStatus = ticTacToe.move(getRow(i), getCol(i), Constants.PLAYER);	
				logger.debug("findBestMove::player::gameStatus: " + gameStatus);
				if (gameStatus == Constants.PLAYER_WINS) {
					bestMove = i;
					logger.debug("findBestMove::player::bestMove: " + bestMove);
					return bestMove;
				}
			}
		}

		ticTacToe = new TicTacToe(Constants.GAME_SIZE);
		for (int i = 0; i < Math.pow(2, Constants.GAME_SIZE); i++) {
			if (!m.contains(i)) {
				logger.debug("findBestMove::machine::i: " + i);
				logger.debug("findBestMove::machine::getRow("+i+"): " + getRow(i));
				logger.debug("findBestMove::machine::getCol("+i+"): " + getCol(i));
				gameStatus = ticTacToe.move(getRow(i), getCol(i), Constants.MACHINE);
				logger.debug("findBestMove::machine::gameStatus: " + gameStatus);
				if (gameStatus == Constants.MACHINE_WINS) {
					bestMove = i;
					logger.debug("findBestMove::machine::bestMove: " + bestMove);
					return bestMove;
				}
			}
		}

		logger.debug("findBestMove::minValues: " + ticTacToe.getMinValue().values());
		int minValue = CommonUtils.findMin(ticTacToe.getMinValue().values());
		logger.debug("findBestMove::minValue: " + minValue);
		Set<Integer> bestMoves = CommonUtils.getKeysByValue(ticTacToe.getMinValue(), minValue);
		logger.debug("findBestMove::bestMoves: " + bestMoves);
		bestMove = bestMoves.stream().findAny().get();
		logger.debug("findBestMove::bestMove: " + bestMove);
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
