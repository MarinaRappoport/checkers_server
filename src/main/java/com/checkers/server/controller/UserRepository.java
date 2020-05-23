package com.checkers.server.controller;

import com.checkers.server.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interface for User CRUD operations & queries
 */
public interface UserRepository extends CrudRepository<User, Long> {
	User findById(long id);

	List<User> findByUsername(String userName);

	Boolean existsByUsername(String userName);
}
