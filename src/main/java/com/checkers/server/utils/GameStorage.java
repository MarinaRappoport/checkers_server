package com.checkers.server.utils;

import com.checkers.server.logic.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class to store games that currently are playing
 */
public class GameStorage {
	private static GameStorage ourInstance = new GameStorage();
	private List<Game> games = new ArrayList<>();

	public static GameStorage getInstance() {
		return ourInstance;
	}

	private GameStorage() {
	}

	public List<Game> getGames() {
		return games;
	}

	public Game findGameByPlayerName(String userName) {
		for (Game game : games) {
			if (game.getBlack().getName().equals(userName) || game.getWhite().getName().equals(userName))
				return game;
		}
		return null;
	}
}
