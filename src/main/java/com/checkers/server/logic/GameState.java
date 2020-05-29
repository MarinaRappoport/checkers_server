package com.checkers.server.logic;

/**
 * Rules:
 * 1) black player starts
 * 2)
 */
public class GameState {

	private Board board;
	private Color currentPlayerColor;
	private Color winnerColor = null;
	private boolean isGameOver = false;

	public GameState(Board board) {
		this.board = board;
		this.currentPlayerColor = Color.BLACK; //black starts the game
	}

	public void applyMove(Move move){
		//if legal
		for (Move legalMove: getLegalMovesCollection().getMoves()) {
			if(legalMove.equals(move)){
				Piece piece = board.getPieceAt(move.piece.getPosition());
				//apply move
				if(move.isJump()){
					Piece jumpedOverPiece = null;
				}else {
					piece.setPosition(move.position);
				}
				board.verifyCrown(piece);
				this.currentPlayerColor = currentPlayerColor==Color.BLACK? Color.WHITE : Color.BLACK;
				return;
			}
		}

		//else - warning
		System.out.println("Move is not legal!");
	}

	/**
	 * @return the current player's collection of valid movements
	 */
	public MovesCollection getLegalMovesCollection(){
		return  null;
	}

	/**
	 * @return the current player's collection of valid Jump movements
	 */
	public MovesCollection getJumpMovesCollection(){
		return  null;
	}

	/**
	 * @return the current player's collection of valid Simple movements
	 */
	public MovesCollection getSimpleMovesCollection(){
		return null;
	}

	public boolean isGameOver(){
		return isGameOver;
	}

	public Color getWinnerColor(){
		return winnerColor;
	}

	public Color getCurrentPlayerColor(){
		return currentPlayerColor;
	}
}
