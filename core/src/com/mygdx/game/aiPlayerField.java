package com.mygdx.game;


public class aiPlayerField extends SeaField {

    private autoIntelect ai;

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
    }
    public int[] doStrike() {
        return ai.doStrikeCalculation();
    }
    public void hearEcho(int[] strikeCoordinate, CellState strikeEcho) {
        ai.strikeLearning(strikeCoordinate,strikeEcho);
    }
}
