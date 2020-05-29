package com.checkers.server.logic;

public class JumpMove extends Move {
	public JumpMove(Piece piece, Position position) {
		super(piece, position);
	}

	@Override
	public boolean isJump() {
		return true;
	}

	@Override
	public String toString() {
		return "Jump: " + piece + " to destination " + position;
	}
}
