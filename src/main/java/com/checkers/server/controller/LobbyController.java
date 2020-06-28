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

@Controller
public class LobbyController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/invite")
	public void startGame(Principal principal, UserDestination userDestination) {
		String from = principal.getName();
		RequestedToStartGame requestedToStartGame = new RequestedToStartGame(from);

		messagingTemplate.convertAndSendToUser(
				userDestination.getToUser(),
				"/queue/invite",
				requestedToStartGame
		);
	}

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

		messagingTemplate.convertAndSendToUser(from, "/queue/start_game", game);
		messagingTemplate.convertAndSendToUser(to, "/queue/start_game", game);
	}


	@MessageMapping("/move")
	public void move(Principal principal, UserMove userMove) {
		String user = principal.getName();
		String opponent;

		//move
		Position from = userMove.getFrom();
		Position to = userMove.getTo();

		//find Game
		GameStorage gameStorage = GameStorage.getInstance();
		Game game = gameStorage.findGameByPlayerName(user);
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
				checkGameStatus(gameStorage, game);

				//send Game status
				messagingTemplate.convertAndSendToUser(user, "/queue/after_move", game);
				messagingTemplate.convertAndSendToUser(opponent, "/queue/after_move", game);
			} else
				messagingTemplate.convertAndSendToUser(user, "/queue/after_move", new GameError("Move is not valid"));
		} else {
			messagingTemplate.convertAndSendToUser(user, "/queue/after_move", new GameError("Game is not found"));
		}
	}

	private void checkGameStatus(GameStorage gameStorage, Game game) {
		if (game.isGameOver()) {
			gameStorage.getGames().remove(game);
			User black = userRepository.findByUsername(game.getBlack().getName()).get(0);
			User white = userRepository.findByUsername(game.getWhite().getName()).get(0);
			GameResult gameResult = new GameResult(white, black);
			gameResult.setWinner(game.getWinnerColor().toString());
			gameRepository.save(gameResult);
			//update users score
			//if draw
			Color winnerColor = game.getWinnerColor();
			if (winnerColor == null) {
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
