package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class aiPlayerField extends SeaField {

    private autoIntelect ai;
    private boolean testSetAllShip = false;
    private boolean testShowDeadCell = true;
    private Texture deadCellTexture;
    private Texture seaTexture;

    public aiPlayerField(int x, int y) {
        super(x, y);
        fieldSet = new String[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++){
                fieldSet[i][j] = WATER_CELL;
            }
        ai = new autoIntelect();
        setAllShipOnField();
        playerType = "Computer";
        seaTexture = new Texture("FullWater2.png");
        deadCellTexture = new Texture("seaCell.png");
    }
    public int[] doStrike() {
        return ai.doStrikeCalculation();
    }
    public void hearEcho(int[] strikeCoordinate, String strikeEcho) {
        ai.strikeLearning(strikeCoordinate,strikeEcho);
    }
    public void render(SpriteBatch batch) {
        update();
        batch.draw(seaTexture, x - 112, y - 44);
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (fieldSet[i][j] == SHIP_CELL && fieldIsMy())
                    batch.draw(cellsTexture, x + j * CELL_SIZE, y + i * CELL_SIZE, 0, 0, 30, 30);
                if (fieldSet[i][j] == FIRED_SHIP_CELL)
                    batch.draw(cellsTexture, x + j * CELL_SIZE, y + i * CELL_SIZE, 30, 0, 30, 30);
                if (fieldSet[i][j] == SPLASH_CELL)
                    batch.draw(splashTexture, x + j * CELL_SIZE, y + i * CELL_SIZE);
            }
        }
        if(testShowDeadCell) {
            String[][] f = ai.showYouMind();
            for (int i = 0; i < FIELD_SIZE; i++)
                for (int j = 0; j < FIELD_SIZE; j++)
                    if (f[i][j] == DEAD_CELL)
                        batch.draw(deadCellTexture, x + j * CELL_SIZE - MainClass.NEXT_FIELD_INDENT, y + i * CELL_SIZE);
        }
    }
}
