package com.checkers.server.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Objects;

/**
 * Abstract class for the piece (checker or king)
 */
public abstract class Piece {
	protected Color color;
	protected Position position;

	public Piece(Color color, Position position) {
		this.color = color;
		this.position = position;
	}

	/**
	 * @param position to to check
	 * @return true if piece is in the given position
	 */
	public boolean inPosition(Position position){
		return this.position.equals(position);
	}

	/**
	 * @return an array of diagonal adjacent positions to the current piece,
	 * taking into account the direction the piece can move and the piece's capabilities
	 */
	@JsonIgnore
	public abstract List<Position> getAdjacentPositions();

	/**
	 * @param otherPiece the given piece to jump over
	 * @return the position after jumping over the given piece
	 */
	public Position getAfterJumpPosition(Piece otherPiece){
		return position.getJumpPosition(otherPiece.position);
	}

	/**
	 * @param jumpDestination the given jump target position
	 * @return the skipped position with the given jump target position
	 */
	public Position getJumpedOverPostion(Position jumpDestination){
		return position.getAdjacentPosition(jumpDestination);
	}

	/**
	 * @return true if piece is King and false if piece is regular Checker
	 */
	public abstract boolean isKing();

	/**
	 * @param row row to check
	 * @return true if piece is in the given row
	 */
	public boolean inRow(int row){
		return this.position.getRow()==row;
	}

	public Color getColor() {
		return color;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Piece piece = (Piece) o;
		return color == piece.color &&
				Objects.equals(position, piece.position);
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, position);
	}

	/**
	 * @return copy of the piece
	 */
	public abstract Piece copy();
}
