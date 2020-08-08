package com.checkers.server.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Abstract class for any move
 */
public abstract class Move {
	protected Piece piece;
	protected Position to;

	public Move(Piece piece, Position to) {
		this.piece = piece;
		this.to = to;
	}

	@JsonIgnore
	public abstract boolean isJump();

	public Position getFrom() {
		return piece.position;
	}
	public Position getTo() {
		return to;
	}
}
