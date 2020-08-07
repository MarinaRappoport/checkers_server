package com.checkers.server;


import com.checkers.server.controller.UserRepository;
import com.checkers.server.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

/**
 * Main server Application class
 */
@SpringBootApplication
public class CheckersApplication {
	private static final Logger log = LoggerFactory.getLogger(CheckersApplication.class);

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(CheckersApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initWithUsersLoggedOut() {
		for (User user : userRepository.findAll()) {
			user.setAvailable(false);
			userRepository.save(user);
		}
	}

}
