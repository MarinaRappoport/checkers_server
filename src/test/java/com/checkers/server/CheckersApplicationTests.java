package com.checkers.server;

import com.checkers.server.controller.GameRepository;
import com.checkers.server.controller.UserRepository;
import com.checkers.server.model.GameResult;
import com.checkers.server.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CheckersApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GameRepository gameRepository;

	@Test
	void contextLoads() {
		User u1 = new User("www", "123", "Marina");
		User u2 = new User("qqq", "123", "Max");
		User u3 = new User("rrr", "123", "Miri");
		userRepository.save(u1);
		userRepository.save(u2);
		userRepository.save(u3);

		GameResult g1 = new GameResult(u1,u2);
		GameResult g2 = new GameResult(u1,u3);
		GameResult g3 = new GameResult(u2,u1);
		GameResult g4 = new GameResult(u3,u2);
		GameResult g5 = new GameResult(u3,u1);

		gameRepository.save(g1);
		gameRepository.save(g2);
		gameRepository.save(g3);
		gameRepository.save(g4);
		gameRepository.save(g5);

		List<GameResult> gameResults = gameRepository.findAllGamesForUser(u1);
	}

}
