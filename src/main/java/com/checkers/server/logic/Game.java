package com.checkers.server.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represent Game state
 *
 * Rules:
 * 1) black player starts
 * 2) After making a move, the turn moves to the other player.
 * 3) The player's legal movements are the set of jumps that the player can make if they exist.
 * If there are no jumps, it's the simple set of moves the player can perform.
 * (player has to jump if possible)
 * 4) If at the end of the movement a checker reached the front row of the opponent,
 * the checker should become king.
 * 5) If a player has no pieces left, that player will lose and his opponent will be declared the winner.
 * If a player has no moves left, that player will lose and his opponent will be declared the winner.
 * 6) The game ends in a draw, if during MAX_ROUNDS_WITHOUT_JUMPING consecutive rounds,
 * no jump moves were taken.
 */
public class Game {
	private Player black;
	private Player white;

	private Board board;
	private Color currentPlayerColor;
	private Color winnerColor = null;
	private boolean isGameOver = false;
	private int roundWithoutJumpCount = 0;

	public Game(Player black, Player white) {
		this.black = black;
		this.white = white;
		this.board = new Board();
		this.currentPlayerColor = Color.BLACK; //black starts the game
	}

	public boolean move(Player player, Position from, Position to){
		if(currentPlayerColor==player.getColor())
			return applyMove(from,to);
		return false;
	}

	public boolean applyMove(Position from, Position to) {
		//if legal
		Move move = findLegalMove(from, to);
		if (move != null) {
			Piece piece = board.getPieceAt(move.piece.getPosition());
			//apply move
			if (move.isJump()) {
				JumpMove jumpMove = (JumpMove) move;
				List<Position> intermediatePositions = jumpMove.getIntermediatePositions();
				for (Position intermediatePosition : intermediatePositions) {
					Piece jumpOverPiece = board.getPieceAt(piece.getJumpedOverPostion(intermediatePosition));
					board.removePieceFromBoard(jumpOverPiece);
					piece.setPosition(intermediatePosition);
				}
				board.verifyCrown(piece);
				roundWithoutJumpCount = 0;
			} else {
				piece.setPosition(move.to);
				board.verifyCrown(piece);
				roundWithoutJumpCount++;
			}
			this.currentPlayerColor = currentPlayerColor == Color.BLACK ? Color.WHITE : Color.BLACK;
			checkGameStatus();
			board.printBoard();//for debug
			return true;
		}

		//else - warning
		System.out.println("Move is not legal!");
		return false;
	}

	public Player getBlack() {
		return black;
	}

	public Player getWhite() {
		return white;
	}

	private Move findLegalMove(Position from, Position to) {
		Piece piece = board.getPieceAt(from);
		if (piece != null) {
			for (Move legalMove : getLegalMovesCollection()) {
				if (legalMove.getFrom().equals(piece.position) && legalMove.getTo().equals(to))
					return legalMove;
			}
		}
		return null;
	}

	/**
	 * @return the current player's collection of valid movements
	 */
	public List<Move> getLegalMovesCollection() {
		//jump is mandatory
		List<Move> jumpMoves = getJumpMovesCollection();
		if (jumpMoves.size() > 0) return jumpMoves;
		else return getSimpleMovesCollection();
	}

	/**
	 * @return the current player's collection of valid Jump movements
	 */
	private List<Move> getJumpMovesCollection() {
		List<Move> moves = new ArrayList<>();
		List<Piece> pieces = board.getPieces(currentPlayerColor);
		for (Piece piece : pieces) {
			//recursively find all possible jump sequences for each piece
			findAllJumpMoveForPiece(piece, piece, moves, new JumpMove(piece, null), board);
		}
		return moves;
	}

	/**
	 * @return the current player's collection of valid Simple movements
	 */
	private List<Move> getSimpleMovesCollection() {
		List<Move> moves = new ArrayList<>();
		List<Piece> pieces = board.getPieces(currentPlayerColor);
		for (Piece piece : pieces) {
			for (Position position : piece.getAdjacentPositions()) {
				if (board.getPieceAt(position) == null) moves.add(new SimpleMove(piece, position));
			}
		}
		return moves;
	}

	private void findAllJumpMoveForPiece(Piece originalPiece, Piece afterJumpPiece, List<Move> moves, JumpMove move, Board board) {
		for (Position position : afterJumpPiece.getAdjacentPositions()) {
			Piece otherPiece = board.getPieceAt(position);
			//if checker could attack
			if (otherPiece != null && otherPiece.getColor() != currentPlayerColor) {
				Position jumpPosition = afterJumpPiece.getAfterJumpPosition(otherPiece);
				//there is a place to jump
				if (jumpPosition != null && board.getPieceAt(jumpPosition) == null) {
					JumpMove newMove = new JumpMove(originalPiece, jumpPosition);
					newMove.getIntermediatePositions().addAll(move.getIntermediatePositions());
					newMove.getIntermediatePositions().add(jumpPosition);
					//check that on that to no piece located
					//stop if checker becomes king
					if (board.isToBecomeKing(originalPiece, jumpPosition)) {
						moves.add(newMove);
					} else //continue to search for moves
					{
						Piece newPiece = originalPiece.copy();
						newPiece.setPosition(jumpPosition);
						Board newBoard = board.copy();
						newBoard.removePieceFromBoard(otherPiece);
						findAllJumpMoveForPiece(originalPiece, newPiece, moves, newMove, newBoard);
					}
				} else {
					if (move.getTo() != null) moves.add(move);
				}
			} else {
				if (move.getTo() != null) moves.add(move);
			}
		}
	}

	public void checkGameStatus() {
		if (board.getPiecesNumber(currentPlayerColor) == 0 || getLegalMovesCollection().size() == 0) {
			isGameOver = true;
			winnerColor = currentPlayerColor == Color.BLACK ? Color.WHITE : Color.BLACK;
		} else if (roundWithoutJumpCount == GameConstants.MAX_ROUNDS_WITHOUT_JUMPING) {
			isGameOver = true;
		}
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public Color getWinnerColor() {
		return winnerColor;
	}

	public Color getCurrentPlayerColor() {
		return currentPlayerColor;
	}

	public Board getBoard() {
		return board;
	}
}
