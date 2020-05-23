package com.checkers.server.controller;

import com.checkers.server.model.Game;
import com.checkers.server.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interface for Game CRUD operations & queries
 */
public interface GameRepository extends CrudRepository<Game, Long> {
	@Query("SELECT g FROM Game g WHERE g.white = ?1 or g.black = ?1")
	List<Game> findAllGamesForUser(User user);
}
