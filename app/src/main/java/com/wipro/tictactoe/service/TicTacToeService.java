package com.wipro.tictactoe.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.tictactoe.exception.ErrorMessages;
import com.wipro.tictactoe.exception.PlayerAlreadyExistsException;
import com.wipro.tictactoe.model.Game;
import com.wipro.tictactoe.model.Player;
import com.wipro.tictactoe.repository.GameRepository;
import com.wipro.tictactoe.repository.MoveRepository;
import com.wipro.tictactoe.repository.PlayerRepository;
import com.wipro.tictactoe.response.GameResponse;

@Service
public class TicTacToeService {

	private static final Logger logger = LoggerFactory.getLogger(TicTacToeService.class);

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	GameRepository gameRepository;

	@Autowired
	MoveRepository moveRepository;

	public GameResponse startGame(String playerName) {

		logger.debug("playerName: " + playerName);

		GameResponse gameResponse = null;

		List<Player> players = playerRepository.findByPlayerName(playerName);

		logger.debug("players: " + players);

		if (players.size() == 0) { // New Player

			// Create a new player
			Player player = new Player();
			player.setPlayerName(playerName);
			player.setNumberOfPlays(1);
			player.setNumberOfWins(0);
			Player newPlayer = playerRepository.save(player);

			// Create a new game
			Game game = new Game();
			game.setPlayerId(newPlayer.getId());
			game.setStart(new Date());
			Game newGame = gameRepository.save(game);

			gameResponse = new GameResponse();
			gameResponse.setGameId(newGame.getId());
			gameResponse.setPlayerId(newPlayer.getId());

		} else { // Player already exists
			throw new PlayerAlreadyExistsException(ErrorMessages.PLAYER_ALREADY_EXISTS.getErrorMessage() + " (" + playerName + ")");
		}

		return gameResponse;
	}
}
