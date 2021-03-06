package com.checkers.server.controller;

import com.checkers.server.logic.*;
import com.checkers.server.model.*;
import com.checkers.server.utils.GameStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Random;

/**
 * Controller for all lobby operation via Websocket API
 */
@Controller
public class LobbyController {

	private static final String WS_URL_GAME_STATUS = "/queue/game_status";

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	/**
	 * Invite user to play
	 */
	@MessageMapping("/invite")
	public void invite(Principal principal, UserDestination userDestination) {
		String from = principal.getName();
		RequestedToStartGame requestedToStartGame = new RequestedToStartGame(from);

		messagingTemplate.convertAndSendToUser(
				userDestination.getToUser(),
				"/queue/invite",
				requestedToStartGame
		);
	}

	/**
	 * Accept invitation to play
	 */
	@MessageMapping("/accept")
	public void accept(Principal principal, UserDestination userDestination) {
		String from = principal.getName();
		String to = userDestination.getToUser();

		//set random color
		Boolean isBlack = new Random().nextBoolean();

		Player black = new Player(isBlack ? from : to, Color.BLACK);
		Player white = new Player(isBlack ? to : from, Color.WHITE);

		Game game = new Game(black, white);
		GameStorage.getInstance().getGames().add(game);

		messagingTemplate.convertAndSendToUser(from, WS_URL_GAME_STATUS, game);
		messagingTemplate.convertAndSendToUser(to, WS_URL_GAME_STATUS, game);
	}

	/**
	 * Make new move in the game
	 */
	@MessageMapping("/move")
	public void move(Principal principal, UserMove userMove) {
		String user = principal.getName();
		String opponent;

		//move
		Position from = userMove.getFrom();
		Position to = userMove.getTo();

		//find Game
		Game game = GameStorage.getInstance().findGameByPlayerName(user);
		Player currentPlayer;
		if (game != null) {
			if (game.getBlack().getName().equals(user)) {
				currentPlayer = game.getBlack();
				opponent = game.getWhite().getName();
			} else {
				currentPlayer = game.getWhite();
				opponent = game.getBlack().getName();
			}

			boolean isValidMove = game.move(currentPlayer, from, to);

			if (isValidMove) {
				//if game is over
				checkGameStatusAndUpdateHistory(game);

				//send Game status
				messagingTemplate.convertAndSendToUser(user, WS_URL_GAME_STATUS, game);
				messagingTemplate.convertAndSendToUser(opponent, WS_URL_GAME_STATUS, game);
			} else
				messagingTemplate.convertAndSendToUser(user, WS_URL_GAME_STATUS, new GameError("Move is not valid"));
		} else {
			messagingTemplate.convertAndSendToUser(user, WS_URL_GAME_STATUS, new GameError("Game is not found"));
		}
	}

	/**
	 * Finish the game (surrender)
	 */
	@MessageMapping("/surrender")
	public void surrender(Principal principal) {
		String user = principal.getName();
		String opponent;

		//find Game
		Game game = GameStorage.getInstance().findGameByPlayerName(user);
		if (game != null) {
			if (game.getBlack().getName().equals(user)) {
				opponent = game.getWhite().getName();
				game.setWinnerColor(Color.WHITE);
			} else {
				opponent = game.getBlack().getName();
				game.setWinnerColor(Color.BLACK);
			}

			game.setGameOver(true);

			checkGameStatusAndUpdateHistory(game);

			//send Game status
			messagingTemplate.convertAndSendToUser(user, WS_URL_GAME_STATUS, game);
			messagingTemplate.convertAndSendToUser(opponent, WS_URL_GAME_STATUS, game);
		} else {
			messagingTemplate.convertAndSendToUser(user, WS_URL_GAME_STATUS, new GameError("Game is not found"));
		}
	}

	/**
	 * private method that update the DB history and user's score in case game is over
	 */
	private void checkGameStatusAndUpdateHistory(Game game) {
		if (game.isGameOver()) {
			GameStorage.getInstance().getGames().remove(game);
			User black = userRepository.findByUsername(game.getBlack().getName()).get(0);
			User white = userRepository.findByUsername(game.getWhite().getName()).get(0);
			Color winnerColor = game.getWinnerColor();
			GameResult gameResult = new GameResult(white, black);
			if (winnerColor != null)
				gameResult.setWinner(game.getWinnerColor().toString());
			gameRepository.save(gameResult);
			//update users score
			if (winnerColor == null) { //if draw
				black.setScore(black.getScore() + GameConstants.SCORE_FOR_DRAW);
				white.setScore(black.getScore() + GameConstants.SCORE_FOR_DRAW);
			} else if (winnerColor == Color.BLACK)
				black.setScore(black.getScore() + GameConstants.SCORE_FOR_WIN);
			else white.setScore(black.getScore() + GameConstants.SCORE_FOR_WIN);
			userRepository.save(black);
			userRepository.save(white);
		}
	}
}
