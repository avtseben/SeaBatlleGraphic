package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class aiPlayerField extends SeaField {

    private autoIntelect ai;
    private boolean testSetAllShip = false;
    private boolean testShowDeadCell = false;
    private Texture deadCellTexture;
    private Texture seaTexture;

    public aiPlayerField(int x, int y) {
        super(x, y);
        fieldStateSet = new CellState[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++){
                fieldStateSet[i][j] = CellState.WATER;
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
    public void hearEcho(int[] strikeCoordinate, CellState strikeEcho) {
        ai.strikeLearning(strikeCoordinate,strikeEcho);
    }
    public void render(SpriteBatch batch) {
        update();
        batch.draw(seaTexture, x - 112, y - 44);
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (fieldStateSet[i][j] == CellState.SHIP && fieldIsMy())
                    batch.draw(shipTexture, x + j * CELL_SIZE, y + i * CELL_SIZE);//Данный перегруженнный метод draw позволяет отрисовать часть картинки
                if (fieldStateSet[i][j] == CellState.FIRED)
                    batch.draw(firedTexture, x + j * CELL_SIZE, y + i * CELL_SIZE);//Данный перегруженнный метод draw позволяет отрисовать часть картинки
                if (fieldStateSet[i][j] == CellState.SPLASH)
                    batch.draw(splashTexture, x + j * CELL_SIZE, y + i * CELL_SIZE);
            }
        }
        if(testShowDeadCell) {
            CellState[][] f = ai.showYouMind();
            for (int i = 0; i < FIELD_SIZE; i++)
                for (int j = 0; j < FIELD_SIZE; j++)
                    if (f[i][j] == CellState.DEAD)
                        batch.draw(deadCellTexture, x + j * CELL_SIZE - MainClass.NEXT_FIELD_INDENT, y + i * CELL_SIZE);
        }
    }
}
