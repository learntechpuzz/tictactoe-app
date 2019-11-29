package com.wipro.tictactoe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wipro.tictactoe.model.Move;

public interface MoveRepository extends CrudRepository<Move, Integer> {

	List<Move> findByGameId(int gameId);

	List<Move> findByGameIdAndMove(int gameId, int move);
}
