package com.checkers.server.model;

/**
 * Represents json response with username of the player who invited to play
 */
public class RequestedToStartGame {
    private String fromUser;

    public RequestedToStartGame(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getFromUser() {
        return fromUser;
    }
}
