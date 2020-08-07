package com.checkers.server.logic;

import java.util.List;

/**
 * Represents checker
 */
public class Checker extends Piece {
	public Checker(Color color, Position position) {
		super(color, position);
	}

	@Override
	public List<Position> getAdjacentPositions() {
		return position.getAdjacentPositions(color == Color.BLACK);
	}

	@Override
	public boolean isKing() {
		return false;
	}

	@Override
	public Piece copy() {
		return new Checker(color,position);
	}

	@Override
	public String toString() {
		return "Checker{" + color + position + "}";
	}
}
