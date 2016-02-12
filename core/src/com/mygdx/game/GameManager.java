package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameManager {

    private SeaField player1;
    private aiPlayerField player2;//TODO not flexible
    private boolean player1Turn;
    private boolean gameContinue;
    private BitmapFont fnt;
    private String winnerMessage;
    private boolean testShowCompMind = true;

    public GameManager (SeaField player1, SeaField player2) {
        this.player1 = player1;
        this.player2 = (aiPlayerField)player2;
        player1Turn = true;
        gameContinue = true;
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
    }
    public void render(SpriteBatch batch) {
        update();
        if (!gameContinue) fnt.draw(batch,winnerMessage,400,500);
    }
    public  void update() {
        if(gameContinue) {
            if (player1Turn) {
                if (player2.clickForStrike())
                    player1Turn = false;
                if (!player2.haveAliveShips()) {
                    gameContinue = false;
                    winnerMessage = "Player 1 is Win!";
                }
            } else {
                int strikeCoordinate[] = player2.doStrike();
                String strikeEcho = player1.gotStrike(strikeCoordinate[0], strikeCoordinate[1]);
                player2.hearEcho(strikeCoordinate, strikeEcho);
                if (!player1.haveAliveShips()) {
                    gameContinue = false;
                    winnerMessage = "Player 2 is Win!";
                }
                player1Turn = true;
            }
        }
    }
}
