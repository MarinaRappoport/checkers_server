package com.checkers.server.model;

import javax.persistence.*;

/**
 * Represents "GameResult" table in DB
 */
@Entity
public class GameResult {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer gameId;

	@ManyToOne
	private User white;

	@ManyToOne
	private User black;

	@Transient
	private int score;

	private String winner; //white, black or draw

	private long timestamp;

	public GameResult(User white, User black) {
		this.white = white;
		this.black = black;
		this.timestamp = System.currentTimeMillis();
	}

	public GameResult() {
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
