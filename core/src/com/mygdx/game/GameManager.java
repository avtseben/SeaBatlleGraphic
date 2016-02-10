package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sun.deploy.perf.PerfLabel;

public class GameManager {

    private SeaField player1;
    private aiPlayerField player2;//TODO not flexible
    private boolean player1Turn;

    public GameManager (SeaField player1, SeaField player2) {
        this.player1 = player1;
        this.player2 = (aiPlayerField)player2;
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
            int strikeCoordinate[] = player2.doStrike();
            String strikeEcho = player1.gotStrike(strikeCoordinate[0],strikeCoordinate[1]);
            player2.hearEcho(strikeCoordinate,strikeEcho);

            player1Turn = true;
        }
    }
}
