package com.checkers.server.model;

public class RequestedToStartGame {
    private String fromUser;

    public RequestedToStartGame(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getFromUser() {
        return fromUser;
    }
}
