package com.checkers.server.controller;

import com.checkers.server.logic.Game;
import com.checkers.server.model.GameResult;
import com.checkers.server.model.User;
import com.checkers.server.utils.GameStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


/**
 * Game controller for API calls related to game (history, current game)
 */
@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /api (after Application path)
public class GameController {
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Get history of user's games
	 */
	@GetMapping(path = "/history/{userId}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody
	Iterable<GameResult> getAllGamesForUser(@PathVariable(value = "userId") int userId) {
		User u = userRepository.findById(userId);
		if (u == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
		return gameRepository.findAllGamesForUser(u);
	}

	/**
	 * Get current user's game (return null if user is not playing now)
	 */
	@GetMapping(path = "/game/{userName}")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody
	Game getCurrentGameForUser(@PathVariable(value = "userName") String userName) {
		Game game = GameStorage.getInstance().findGameByPlayerName(userName);
		if (game == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
		}
		return game;
	}
}
