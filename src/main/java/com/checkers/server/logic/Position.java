package com.checkers.server.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.checkers.server.logic.GameConstants.BOARD_SIZE;
import static com.checkers.server.logic.GameConstants.FIRST_POSITION;

/**
 * Represents position of the piece on board
 */
public class Position {
	private int row; // 1 to 8
	private int column; //1 to 8

	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public Position() {
	}

	/**
	 * @param isDownward - true if to look in current row + 1, false - if to look in current row - 1
	 * @return array of nearby diagonal positions within the given line distance
	 */
	public List<Position> getAdjacentPositions(boolean isDownward) {
		List<Position> positions = new ArrayList<>();
		int newRow = isDownward ? this.row + 1 : this.row - 1;
		Position positionLeft = new Position(newRow, column - 1);
		Position positionRight = new Position(newRow, column + 1);
		if (positionLeft.isValid())
			positions.add(positionLeft);
		if (positionRight.isValid())
			positions.add(positionRight);
		return positions;
	}


	/**
	 * @param adjacentPosition to to jump over
	 * @return new to after jump
	 */
	public Position getJumpPosition(Position adjacentPosition) {
		boolean isForward = this.row - adjacentPosition.row < 0;
		boolean isRight = this.column - adjacentPosition.column < 0;
		int newRow = isForward ? adjacentPosition.row + 1 : adjacentPosition.row - 1;
		int newColumn = isRight ? adjacentPosition.column + 1 : adjacentPosition.column - 1;
		Position position = new Position(newRow, newColumn);
		if (position.isValid()) return position;
		else return null;
	}

	/**
	 * @param jumpPosition the given jump target to
	 * @return the skipped to with the given jump target
	 */
	public Position getAdjacentPosition(Position jumpPosition) {
		int newRow = (this.row + jumpPosition.row) / 2;
		int newColumn = (this.column + jumpPosition.column) / 2;
		return new Position(newRow, newColumn);
	}

	/**
	 * @return true if to is located inside the board
	 */
	private boolean isValid() {
		return row <= BOARD_SIZE && row >= FIRST_POSITION && column <= BOARD_SIZE && column >= FIRST_POSITION;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Position position = (Position) o;
		return row == position.row &&
				column == position.column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(row, column);
	}

	@Override
	public String toString() {
		return "[" + row + "," + column + "]";
	}
}
