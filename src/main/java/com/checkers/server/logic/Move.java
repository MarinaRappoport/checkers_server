package com.checkers.server.logic;

import java.util.Objects;

public abstract class Move {
	protected Piece piece;
	protected Position position;

	public Move(Piece piece, Position position) {
		this.piece = piece;
		this.position = position;
	}

	public abstract boolean isJump();

	public Piece getPiece() {
		return piece;
	}

	public Position getPosition() {
		return position;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Move move = (Move) o;
		return Objects.equals(piece, move.piece) &&
				Objects.equals(position, move.position);
	}

	@Override
	public int hashCode() {
		return Objects.hash(piece, position);
	}
}
