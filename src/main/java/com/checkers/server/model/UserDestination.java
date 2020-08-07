package com.checkers.server.model;

/**
 * Represents json with user destination (to who the request was sent)
 */
public class UserDestination {
    private String toUser;

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
}
