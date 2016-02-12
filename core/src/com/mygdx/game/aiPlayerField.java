package com.mygdx.game;

public class aiPlayerField extends SeaField {

    private autoIntelect ai;
    private boolean testSetAllShip = false;

    public aiPlayerField(int x, int y) {
        super(x, y);
        fieldSet = new String[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++){
                fieldSet[i][j] = "water";
            }
        ai = new autoIntelect();
        setAllShipOnField();
        playerType = "Computer";
    }
    public int[] doStrike() {
        return ai.doStrikeCalculation();
    }
    public void hearEcho(int[] strikeCoordinate, String strikeEcho) {
        ai.strikeLearning(strikeCoordinate,strikeEcho);
    }
}
