package com.checkers.server.logic;

import java.util.ArrayList;
import java.util.List;

public class MovesCollection {
	private List<Move> moves = new ArrayList<>();

	public void addMove(Move move){
		moves.add(move);
	}

	public int getMovesNumber(Move move){
		return 0;
	}

	public Move getMoveByIndex(int index){
		return null;
	}

	public List<Move> getMoves() {
		return moves;
	}
}
