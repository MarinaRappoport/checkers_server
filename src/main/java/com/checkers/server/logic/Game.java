package com.checkers.server.logic;

public class Game {
	private Player black;
	private Player white;
	private GameState gameState;

	public Game(Player black, Player white) {
		this.black = black;
		this.white = white;
		this.gameState = new GameState(new Board());
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public void move(Player player, Position from, Position to){
		if(gameState.getCurrentPlayerColor()==player.getColor()) {
			gameState.applyMove(from,to);
		}
	}

	private boolean isGameOver(){
		return gameState.isGameOver();
	}
}
