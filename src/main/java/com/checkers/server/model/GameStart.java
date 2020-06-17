package com.checkers.server.model;

public class GameStart {
    private String player1, player2;

    public GameStart(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }
}
