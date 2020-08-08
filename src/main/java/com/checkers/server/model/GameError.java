package com.checkers.server.model;

/**
 * Represents json response with game error
 */
public class GameError {
	private String error;

	public GameError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
}
