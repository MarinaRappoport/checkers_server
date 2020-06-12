package com.checkers.server.model;

public class RequestToStartGame {
    private String toUser;

    public RequestToStartGame(String toUser) {
        this.toUser = toUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
}
