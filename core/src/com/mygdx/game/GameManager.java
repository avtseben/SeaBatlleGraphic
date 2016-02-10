package com.mygdx.game;

public class GameManager {

    private SeaField player1;
    private SeaField player2;
    private boolean player1Turn;

    public GameManager (SeaField player1, SeaField player2) {
        this.player1 = player1;
        this.player2 = player2;
        player1Turn = true;
        startGame();
    }
    public void startGame() {

        if(player1Turn) {
            player2.gotStrike(2,2);
        }
    }
}
