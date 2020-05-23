package com.checkers.server.controller;

import com.checkers.server.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Game controller for building API
 */
@Controller // This means that this class is a Controller
@RequestMapping(path="/api") // This means URL's start with /api (after Application path)
public class GameController {
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping(path="/history")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody
	Iterable<Game> getAllGamesForUser(@RequestParam long userId) {
		// This returns a JSON or XML with the games
		return gameRepository.findAllGamesForUser(userRepository.findById(userId));
	}
}
