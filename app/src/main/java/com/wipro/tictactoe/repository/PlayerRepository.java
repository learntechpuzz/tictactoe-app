package com.wipro.tictactoe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wipro.tictactoe.model.Player;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

	List<Player> findByPlayerName(String playerName);
}
