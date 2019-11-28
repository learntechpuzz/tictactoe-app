package com.wipro.tictactoe.repository;

import org.springframework.data.repository.CrudRepository;

import com.wipro.tictactoe.model.Game;

public interface GameRepository extends CrudRepository<Game, Integer>{

}
