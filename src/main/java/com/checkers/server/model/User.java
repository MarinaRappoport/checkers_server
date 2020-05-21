package com.checkers.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name="Users")
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	@Column(unique=true)
	private String username;
	private String name;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private String status = "available";
	private long score = 0;

	public User(String username, String password, String name) {
		this.username = username;
		this.password = password;
		this.name = name;
	}

	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}
}
