package com.wipro.tictactoe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wipro.tictactoe.model.Game;

public interface GameRepository extends CrudRepository<Game, Integer> {
	
	@Query("SELECT g FROM Game g WHERE g.playerId = :playerId and g.id in (SELECT coalesce(max(g.id), 0) from Game g WHERE g.playerId = :playerId)")
	public Game findLastGame(@Param("playerId") int playerId);
}
