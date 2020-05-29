package com.checkers.server.logic;

import java.util.ArrayList;
import java.util.List;

public class JumpMove extends Move {
	private List<Position> intermediatePositions = new ArrayList<>();

	public JumpMove(Piece piece, Position position) {
		super(piece, position);
	}

	public List<Position> getIntermediatePositions() {
		return intermediatePositions;
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
