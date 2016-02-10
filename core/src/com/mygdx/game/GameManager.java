package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameManager {

    private SeaField player1;
    private SeaField player2;
    private boolean player1Turn;

    public GameManager (SeaField player1, SeaField player2) {
        this.player1 = player1;
        this.player2 = player2;
        player1Turn = true;
    }
    public void render(SpriteBatch batch) {
        update();
    }
    public  void update() {
        if(player1Turn) {
            if (player2.clickForStrike())
                player1Turn = false;
        }
        else {
            player1.gotStrike(2,2);
            player1Turn = true;
        }
    }
}
