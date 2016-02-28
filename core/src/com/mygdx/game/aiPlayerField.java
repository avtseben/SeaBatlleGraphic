package com.mygdx.game;


public class aiPlayerField extends SeaField {

    private autoIntelect ai;

    public aiPlayerField(int x, int y) {
        super(x, y);
        ai = new autoIntelect();
        playerType = "Computer";
    }
    public int[] doStrike() {
        return ai.doStrikeCalculation();
    }
    public void hearEcho(int[] strikeCoordinate, CellState strikeEcho) {
        ai.strikeLearning(strikeCoordinate,strikeEcho);
    }
}
