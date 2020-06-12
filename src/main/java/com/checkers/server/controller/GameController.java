package com.checkers.server.controller;

import com.checkers.server.model.GameResult;
import com.checkers.server.model.RequestToStartGame;
import com.checkers.server.model.RequestedToStartGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


/**
 * GameResult controller for building API
 */
@Controller // This means that this class is a Controller
@RequestMapping(path="/api") // This means URL's start with /api (after Application path)
public class GameController {
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@GetMapping(path="/history")
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody
	Iterable<GameResult> getAllGamesForUser(@RequestParam long userId) {
		// This returns a JSON or XML with the games
		return gameRepository.findAllGamesForUser(userRepository.findById(userId));
	}

	@MessageMapping("/start_game")
	public void startGame(Principal principal, RequestToStartGame requestToStartGame) {
		String from = principal.getName();
		RequestedToStartGame requestedToStartGame = new RequestedToStartGame(from);

		messagingTemplate.convertAndSendToUser(
			requestToStartGame.getToUser(),
			"/queue/startGame",
			requestedToStartGame
		);
	}
}
