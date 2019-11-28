package com.wipro.tictactoe.repository;

import org.springframework.data.repository.CrudRepository;

import com.wipro.tictactoe.model.Move;

public interface MoveRepository extends CrudRepository<Move, Integer>{

}
