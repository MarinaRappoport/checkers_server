package com.checkers.server.controller;

import com.checkers.server.model.GameResult;
import com.checkers.server.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interface for GameResult CRUD operations & queries
 */
public interface GameRepository extends CrudRepository<GameResult, Long> {
	@Query("SELECT g FROM GameResult g WHERE g.white = ?1 or g.black = ?1")
	List<GameResult> findAllGamesForUser(User user);
}
