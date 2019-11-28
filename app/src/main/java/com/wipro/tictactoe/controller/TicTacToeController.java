package com.wipro.tictactoe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.tictactoe.service.TicTacToeService;

@RestController
public class TicTacToeController {

	@Autowired
	TicTacToeService service;

	
	@GetMapping("/add")
	public int sum(@RequestParam(value = "number1", required = true) int number1,
			@RequestParam(value = "number2", required = true) int number2) {
		return service.add(number1, number2);
	}
}
