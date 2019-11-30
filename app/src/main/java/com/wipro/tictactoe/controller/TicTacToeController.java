package com.wipro.tictactoe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.tictactoe.response.MoveResponse;
import com.wipro.tictactoe.response.StartResponse;
import com.wipro.tictactoe.service.TicTacToeService;

@RestController
public class TicTacToeController {

	@Autowired
	TicTacToeService service;

	@GetMapping("/start")
	public StartResponse sum(@RequestParam(value = "playerName", required = true) String playerName) {
		return service.startGame(playerName);
	}

	@GetMapping("/restart")
	public StartResponse sum(@RequestParam(value = "playerId", required = true) int playerId,
			@RequestParam(value = "gameId", required = true) int gameId) {
		return service.restartGame(playerId, gameId);
	}

	@GetMapping("/move")
	public MoveResponse move(@RequestParam(value = "gameId", required = true) int gameId,
			@RequestParam(value = "move", required = true) int move) {
		return service.move(gameId, move);
	}

}
