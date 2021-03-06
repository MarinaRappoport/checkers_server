package com.checkers.server.model;

import com.checkers.server.logic.Position;

/**
 * Represents json request with move
 */
public class UserMove {
	private Position from;
	private Position to;

	public Position getFrom() {
		return from;
	}

	public void setFrom(Position from) {
		this.from = from;
	}

	public Position getTo() {
		return to;
	}

	public void setTo(Position to) {
		this.to = to;
	}
}
