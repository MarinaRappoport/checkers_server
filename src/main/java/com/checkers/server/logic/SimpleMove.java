package com.checkers.server.logic;

/**
 * Represents simple move
 */
public class SimpleMove extends Move {
	public SimpleMove(Piece piece, Position position) {
		super(piece, position);
	}

	@Override
	public boolean isJump() {
		return false;
	}

	@Override
	public String toString() {
		return "Simple: " + piece + " to destination " + to;
	}
}
