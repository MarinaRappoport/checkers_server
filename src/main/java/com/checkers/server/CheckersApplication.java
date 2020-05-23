package com.checkers.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main server Application class
 */
@SpringBootApplication
public class CheckersApplication {
	private static final Logger log = LoggerFactory.getLogger(CheckersApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CheckersApplication.class, args);
	}

}
