package com.checkers.server.logic;

import java.util.List;

public class Checker extends Piece {
	public Checker(Color color, Position position) {
		super(color, position);
	}

	@Override
	public List<Position> getAdjacentPositions() {
		return position.getAdjacentPositions(color == Color.WHITE);
	}

	@Override
	public boolean isKing() {
		return false;
	}

	@Override
	public String toString() {
		return "Checker{" +
				"color=" + color +
				", position=" + position +
				'}';
	}
}
