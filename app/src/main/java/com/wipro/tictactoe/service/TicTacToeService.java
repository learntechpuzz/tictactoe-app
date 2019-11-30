package com.wipro.tictactoe.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.tictactoe.exception.ErrorMessages;
import com.wipro.tictactoe.exception.GameNotFoundException;
import com.wipro.tictactoe.exception.MoveNotAllowedException;
import com.wipro.tictactoe.model.Game;
import com.wipro.tictactoe.model.Move;
import com.wipro.tictactoe.model.Player;
import com.wipro.tictactoe.repository.GameRepository;
import com.wipro.tictactoe.repository.MoveRepository;
import com.wipro.tictactoe.repository.PlayerRepository;
import com.wipro.tictactoe.response.MoveResponse;
import com.wipro.tictactoe.response.StartResponse;
import com.wipro.tictactoe.utils.Constants;
import com.wipro.tictactoe.utils.TicTacToeUtils;

@Service
public class TicTacToeService {

	private static final Logger logger = LoggerFactory.getLogger(TicTacToeService.class);

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	GameRepository gameRepository;

	@Autowired
	MoveRepository moveRepository;

	@Autowired
	TicTacToeUtils ticTacToeUtils;

	/**
	 * Add a new player and create a new game for the player
	 * 
	 * @param playerName
	 * @return
	 */
	@Transactional
	public StartResponse startGame(String playerName) {

		logger.debug("playerName: " + playerName);

		StartResponse response = null;

		List<Player> players = playerRepository.findByPlayerName(playerName);

		if (players != null && players.size() == 0) { // New player

			// Add a new player
			Player player = new Player();
			player.setPlayerName(playerName);
			player.setNumberOfPlays(1);
			player.setNumberOfWins(0);
			Player newPlayer = playerRepository.save(player);

			// Create a new game for the player
			Game game = new Game();
			game.setPlayerId(newPlayer.getId());
			game.setStart(new Date());
			game.setStatus(Constants.GAME_IN_PROGRESS);
			Game newGame = gameRepository.save(game);

			// game response
			response = new StartResponse();
			response.setPlayerId(newPlayer.getId());
			response.setGameId(newGame.getId());
			response.setStatus(newGame.getStatus());
			response.setMoves(moveRepository.findByGameId(game.getId()));

		} else {

			int playerId = players.get(0).getId();
			logger.debug("playerId: " + playerId);

			Game game = gameRepository.findLastGame(playerId);
			logger.debug("game: " + game);

			// game response
			response = new StartResponse();
			response.setPlayerId(playerId);
			response.setGameId(game.getId());
			response.setStatus(game.getStatus());
			response.setMoves(moveRepository.findByGameId(game.getId()));
		}

		return response;
	}

	/**
	 * Save player move, calculate machine move along with the game status
	 * 
	 * 
	 * @param gameId
	 * @param move
	 * @return
	 */
	@Transactional
	public MoveResponse move(int gameId, int move) {

		logger.debug("move::gameId: " + gameId);
		logger.debug("move::move: " + move);

		MoveResponse response = null;

		Optional<Game> game = gameRepository.findById(gameId);
		if (!game.isPresent())
			throw new GameNotFoundException(ErrorMessages.GAME_NOT_FOUND.getErrorMessage() + " (" + gameId + ")");

		List<Move> moves = moveRepository.findByGameIdAndMove(gameId, move);

		logger.debug("move::findByGameIdAndMove::moves: " + moves.toString());

		if ((moves != null && moves.size() == 0) && move <= Math.pow(2, Constants.GAME_SIZE)
				&& game.get().getStatus() == Constants.GAME_IN_PROGRESS) { // Valid move

			moves = moveRepository.findByGameId(gameId);

			logger.debug("move::existing moves: " + moves.toString());

			response = new MoveResponse();

			// Save player move
			Move playerMove = new Move();
			playerMove.setGameId(gameId);
			playerMove.setMove(move);
			playerMove.setPlayer(Constants.PLAYER); // Player
			Move newPlayerMove = moveRepository.save(playerMove);

			// Include saved player move into moves
			moves.add(newPlayerMove);

			logger.debug("move::after adding player move::moves: " + moves.toString());

			// Check if Player wins
			if (ticTacToeUtils.checkGameStatus(moves) == Constants.PLAYER_WINS) {
				logger.debug("move::Player wins");
				// Save game status as Player wins
				game.get().setStatus(Constants.PLAYER_WINS);
				game.get().setEnd(new Date());
				gameRepository.save(game.get());
				// return response
				response.setStatus(Constants.PLAYER_WINS);
				return response;
			}

			// Check if Game draw
			if (moves.size() == (Math.pow(2, Constants.GAME_SIZE) + 1)) {
				logger.debug("move::Game draw");
				game.get().setStatus(Constants.NO_ONE_WINS);
				game.get().setEnd(new Date());
				gameRepository.save(game.get());
				response.setStatus(Constants.NO_ONE_WINS);
				return response;
			}

			// Find best move
			int bestMove = ticTacToeUtils.findBestMove(moves);
			logger.debug("move::bestMove: " + bestMove);
			
			// Find machine best move (AI)
			Move machineMove = new Move();
			machineMove.setGameId(gameId);
			machineMove.setMove(bestMove);
			machineMove.setPlayer(Constants.MACHINE);

			// Save the machine best move
			Move newMachineMove = moveRepository.save(machineMove);

			// Include saved machine best move into moves
			moves.add(newMachineMove);
			
			
			logger.debug("move::after adding machine move::moves: " + moves.toString());
			
			// set the saved machine best move in response
			response.setMove(newMachineMove);

			// Check if Machine wins
			if (ticTacToeUtils.checkGameStatus(moves) == Constants.MACHINE_WINS) {
				logger.debug("move::Machine wins");
				// Save game status as machine wins
				game.get().setStatus(Constants.MACHINE_WINS);
				game.get().setEnd(new Date());
				gameRepository.save(game.get());
				response.setStatus(Constants.MACHINE_WINS);
			}
		} else { // Invalid move
			throw new MoveNotAllowedException(ErrorMessages.MOVE_NOT_ALLOWED.getErrorMessage() + " (" + move + ")");
		}

		return response;
	}

	/**
	 * Restart the game
	 * 
	 * @param gameId
	 * @return
	 */
	@Transactional
	public StartResponse restartGame(int playerId, int gameId) {

		logger.debug("playerId: " + playerId);
		logger.debug("gameId: " + gameId);

		StartResponse response = null;

		Optional<Game> game = gameRepository.findById(gameId);

		if (game.isPresent()) { // New player

			// Update game
			game.get().setStart(new Date());
			game.get().setEnd(null);
			game.get().setStatus(Constants.GAME_IN_PROGRESS);
			Game updatedGame = gameRepository.save(game.get());

			// Find list of moves for this game
			List<Move> moves = moveRepository.findByGameId(gameId);

			// Delete all moves
			moveRepository.deleteAll(moves);

			// game response
			response = new StartResponse();
			response.setPlayerId(playerId);
			response.setGameId(updatedGame.getId());
			response.setStatus(updatedGame.getStatus());

		} else { // Game not found
			throw new GameNotFoundException(ErrorMessages.GAME_NOT_FOUND.getErrorMessage() + " (" + gameId + ")");
		}

		return response;
	}

}
