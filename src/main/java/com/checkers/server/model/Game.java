package com.checkers.server.model;

import javax.persistence.*;

/**
 * Represents "Game" table in DB
 */
@Entity
public class Game {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer gameId;

	@ManyToOne
	private User white;

	@ManyToOne
	private User black;

	private String winner; //white, black or draw

	private long timestamp;

	public Game(User white, User black) {
		this.white = white;
		this.black = black;
		this.timestamp = System.currentTimeMillis();
	}

	public Game() {
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public User getWhite() {
		return white;
	}

	public void setWhite(User white) {
		this.white = white;
	}

	public User getBlack() {
		return black;
	}

	public void setBlack(User black) {
		this.black = black;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
