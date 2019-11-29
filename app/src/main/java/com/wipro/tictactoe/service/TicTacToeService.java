package com.wipro.tictactoe.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.tictactoe.exception.ErrorMessages;
import com.wipro.tictactoe.exception.MoveNotAllowedException;
import com.wipro.tictactoe.exception.PlayerAlreadyExistsException;
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
			game.setStatus(Constants.NO_ONE_WINS);
			Game newGame = gameRepository.save(game);

			// game response
			response = new StartResponse();
			response.setPlayerId(newPlayer.getId());
			response.setGameId(newGame.getId());
			response.setMoves(moveRepository.findByGameId(newGame.getId()));
			response.setStatus(newGame.getStatus());

		} else { // Player already exists
			throw new PlayerAlreadyExistsException(
					ErrorMessages.PLAYER_ALREADY_EXISTS.getErrorMessage() + " (" + playerName + ")");
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
	public MoveResponse move(int gameId, int move) {

		logger.debug("gameId: " + gameId);

		logger.debug("move: " + move);

		MoveResponse response = null;

		List<Move> moves = moveRepository.findByGameIdAndMove(gameId, move);
		Optional<Game> game = gameRepository.findById(gameId);

		if (moves != null && moves.size() == 0) { // Valid move

			response = new MoveResponse();

			// Save player move
			Move playerMove = new Move();
			playerMove.setGameId(gameId);
			playerMove.setMove(move);
			playerMove.setPlayer(Constants.PLAYER); // Player
			Move newPlayerMove = moveRepository.save(playerMove);

			// Include saved player move into moves
			moves.add(newPlayerMove);

			// Check if Player wins
			if (ticTacToeUtils.checkGameStatus(moves) == Constants.PLAYER_WINS) {
				// Save game status as Player wins
				game.get().setStatus(Constants.PLAYER_WINS);
				game.get().setEnd(new Date());
				gameRepository.save(game.get());
				// return response
				response.setStatus(Constants.PLAYER_WINS);
				return response;
			}

			// Find machine best move (AI)
			Move machineMove = new Move();
			machineMove.setGameId(gameId);
			machineMove.setMove(ticTacToeUtils.findBestMove(moves));
			machineMove.setPlayer(Constants.MACHINE);

			// Save the machine best move
			Move newMachineMove = moveRepository.save(machineMove);

			// Include saved machine best move into moves
			moves.add(newMachineMove);
			// set the saved machine best move in response
			response.setMove(newMachineMove);

			// Check if Machine wins
			if (ticTacToeUtils.checkGameStatus(moves) == Constants.MACHINE_WINS) {
				// Save game status as machine wins
				game.get().setStatus(Constants.MACHINE_WINS);
				game.get().setEnd(new Date());
				gameRepository.save(game.get());
			}

		} else { // Invalid move
			throw new MoveNotAllowedException(ErrorMessages.MOVE_NOT_ALLOWED.getErrorMessage() + " (" + move + ")");
		}

		return response;
	}
}
