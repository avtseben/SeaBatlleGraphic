package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameManager {

    private HumanPlayer player1;
    private AiPlayer player2;//TODO not flexible
    private boolean player1Turn;
    private boolean gameContinue;
    private BitmapFont fnt;
    private String winnerMessage;
    private Texture backGround;

    public GameManager (HumanPlayer player1, AiPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        player1Turn = true;
        gameContinue = true;
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
        backGround = new Texture("backGround.png");
    }
    public void render(SpriteBatch batch) {
        update();
        batch.draw(backGround, 0 , 0);
        if (!gameContinue) fnt.draw(batch,winnerMessage,400,500);
        player1.getField().render(batch, true, 1);
        player2.getField().render(batch, false, 2);
    }
    public  void update() {
        if(gameContinue) {
            if (player1Turn) {
                if (player2.getField().clickForStrike())
                    player1Turn = false;
                if (!player2.getField().haveAliveShips()) {
                    gameContinue = false;
                    winnerMessage = "Player 1 is Win!";
                }
            } else {
                int strikeCoordinate[] = player2.doStrike();
                CellState strikeEcho = player1.getField().gotStrike(strikeCoordinate[0], strikeCoordinate[1]);
                player2.hearEcho(strikeCoordinate, strikeEcho);
                if (!player1.getField().haveAliveShips()) {
                    gameContinue = false;
                    winnerMessage = "Player 2 is Win!";
                }
                player1Turn = true;
            }
        }
    }
}
