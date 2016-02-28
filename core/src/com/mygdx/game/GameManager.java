package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameManager {

    private HumanPlayer player1;
    private AiPlayer player2;//TODO not flexible
    private BitmapFont fnt;
    private String winnerMessage;
    private Texture backGround;
    private GameState gState;

    public GameManager (HumanPlayer player1, AiPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        gState = GameState.Player1Turn;
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
        backGround = new Texture("backGround.png");
    }
    public void render(SpriteBatch batch) {
        update();
        batch.draw(backGround, 0 , 0);
        player1.getField().render(batch, true, 1);
        player2.getField().render(batch, false, 2);
        if(gState == GameState.GameOver) {
            fnt.draw(batch,winnerMessage,400,500);
            player1.getField().render(batch, true, 1);
            player2.getField().render(batch, true, 2);
        }

    }
    public  void update() {
        switch (gState) {
            case Player1Turn:
                if(player1.turn(player2.getField()) == TurnResult.Miss) gState = GameState.Player2Turn;
                break;
            case Player2Turn:
                if(player2.turn(player1.getField()) == TurnResult.Miss) gState = GameState.Player1Turn;
                break;
        }
        if(gState != GameState.GameOver) {
            if (player1.getField().isDefeated()) {
                winnerMessage = "Player 2 is Win!";
                gState = GameState.GameOver;
            } else if (player2.getField().isDefeated()) {
                winnerMessage = "Player 1 is Win!";
                gState = GameState.GameOver;
            }

        }
    }
}
