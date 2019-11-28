package com.wipro.tictactoe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.tictactoe.response.GameResponse;
import com.wipro.tictactoe.service.TicTacToeService;

@RestController
public class TicTacToeController {

	@Autowired
	TicTacToeService service;

	@GetMapping("/start")
	public GameResponse sum(@RequestParam(value = "playerName", required = true) String playerName) {
		return service.startGame(playerName);
	}
}
