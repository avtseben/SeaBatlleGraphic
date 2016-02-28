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
    private String message;
    private Texture backGround;
    private GameState gState;

    public GameManager (HumanPlayer player1, AiPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        gState = GameState.Player1Turn;
        message = "";
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
        backGround = new Texture("backGround.png");
    }
    public void render(SpriteBatch batch) {
        update();
        batch.draw(backGround, 0 , 0);
        if(gState == GameState.Player1Turn) {
            fnt.draw(batch,message,MainClass.NEXT_FIELD_INDENT+SeaField.FIELD_SIZE*SeaField.CELL_SIZE/2,500);
        }
        //if(gState == GameState.Player2Turn) {
        //    fnt.draw(batch,message,MainClass.LEFT_INDENT+SeaField.FIELD_SIZE*SeaField.CELL_SIZE/2,500);
        //}
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
                TurnResult turnResult = player1.turn(player2.getField());
                if(turnResult == TurnResult.Miss) {
                    message = "";
                    gState = GameState.Player2Turn;
                }
                else if(turnResult == TurnResult.Hit) message = "Попал";
                else if(turnResult == TurnResult.Kill) message = "Потопил";
                break;
            case Player2Turn:
                turnResult = player2.turn(player1.getField());
                if(turnResult == TurnResult.Miss) {
                //    message = "Мимо";
                    gState = GameState.Player1Turn;
                }
                //else if(turnResult == TurnResult.Hit) message = "Попал";
                //else if(turnResult == TurnResult.Kill) message = "Потопил";
                break;
        }
        if(gState != GameState.GameOver) {
            if (player1.getField().isDefeated()) {
                winnerMessage = "Французы победили";
                gState = GameState.GameOver;
            } else if (player2.getField().isDefeated()) {
                winnerMessage = "Пираты победили";
                gState = GameState.GameOver;
            }

        }
    }
}
