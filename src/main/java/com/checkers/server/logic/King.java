package com.checkers.server.logic;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
	public King(Color color, Position position) {
		super(color, position);
	}

	@Override
	public List<Position> getAdjacentPositions() {
		List<Position> positions = new ArrayList<>();
		positions.addAll(position.getAdjacentPositions(true));
		positions.addAll(position.getAdjacentPositions(false));
		return positions;
	}

	@Override
	public boolean isKing() {
		return true;
	}

	@Override
	public Piece copy() {
		return new King(color,position);
	}

	@Override
	public String toString() {
		return "King{" + color + position + "}";
	}
}
