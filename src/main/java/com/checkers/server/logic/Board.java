package com.checkers.server.logic;

import java.util.ArrayList;
import java.util.List;

import static com.checkers.server.logic.GameConstants.*;

/**
 * Represents board for checkers game
 */
public class Board {

	private List<Piece> pieces;

	public Board() {
		pieces = new ArrayList<>();

		//init all pieces
		//black
		initPieces(1, 2, Color.BLACK);

		//white
		initPieces(6, 1, Color.WHITE);
	}

	private void initPieces(int row, int column, Color color) {
		int numberOfPieces = 0;
		while (numberOfPieces < NUMBER_OF_PIECES_EACH_COLOR) {
			Piece piece = new Checker(color, new Position(row, column));
			pieces.add(piece);
			if (column == BOARD_SIZE) {
				column = 1;
				row++;
			} else if (column == BOARD_SIZE - 1) {
				column = 2;
				row++;
			} else column = column + 2;
			numberOfPieces++;
		}
	}

	/**
	 * @param color color specified
	 * @return all pieces with color specified
	 */
	public List<Piece> getPieces(Color color) {
		List<Piece> piecesOneColor = new ArrayList<>();
		for (Piece piece : pieces) {
			if (piece.getColor() == color) piecesOneColor.add(piece);
		}
		return piecesOneColor;
	}

	/**
	 * @param position position to check
	 * @return piece in the position specified. if no piece found returns null
	 */
	public Piece getPieceAt(Position position) {
		for (Piece piece : pieces) {
			if (piece.inPosition(position)) return piece;
		}

		return null;
	}

	/**
	 * Remove piece from the board
	 *
	 * @param piece piece to remove
	 */
	public void removePieceFromBoard(Piece piece) {
		pieces.remove(piece);
	}

	/**
	 * @param color color specified
	 * @return number of pieces with color specified
	 */
	public int getPiecesNumber(Color color) {
		int count = 0;
		for (Piece piece : pieces) {
			if (piece.getColor() == color) count++;
		}
		return count;
	}

	/**
	 * Verify that checker is in position to become king and replace checker to king on board
	 *
	 * @param piece piece to verify
	 */
	public void verifyCrown(Piece piece) {
		if (piece.isKing()) return;
		if (piece.getColor() == Color.WHITE && piece.inRow(FIRST_POSITION)
				|| piece.getColor() == Color.BLACK && piece.inRow(BOARD_SIZE)) {
			Piece king = new King(piece.getColor(), piece.getPosition());
			pieces.remove(piece);
			pieces.add(king);
		}
	}

	/**
	 * Check if the piece will become king on the given position
	 * @param piece piece to check
	 * @param position given position
	 * @return true if the piece will become King, else false
	 */
	public boolean isToBecomeKing(Piece piece, Position position){
		if (piece.isKing()) return false;
		return  piece.getColor() == Color.WHITE && position.getRow()==FIRST_POSITION
				|| piece.getColor() == Color.BLACK && position.getRow() == BOARD_SIZE;
	}

	/**
	 * method is used for debugging
	 */
	public void printBoard() {
		System.out.println("-------------------------------");
		for (Piece piece : pieces) {
			System.out.println(piece);
		}
	}

	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
	}

	public List<Piece> getPieces() {
		return pieces;
	}

	/**
	 * @return copy of the board
	 */
	public Board copy(){
		Board board = new Board();
		List<Piece> piecesCopy = new ArrayList<>();
		for (Piece piece: pieces) {
			piecesCopy.add(piece.copy());
			board.setPieces(piecesCopy);
		}
		return board;
	}
}
