package com.wipro.tictactoe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Move {

	@Id
	@GeneratedValue
	private int id;
	private int gameId;
	private int move;
	private int player; // 0 - Machine, 1 - Player

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	@Override
	public String toString() {
		return "Move [id=" + id + ", gameId=" + gameId + ", move=" + move + ", player=" + player + "]";
	}
	
	

}
