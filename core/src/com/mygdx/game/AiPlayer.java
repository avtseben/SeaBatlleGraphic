package com.mygdx.game;

public class AiPlayer extends Player {

    private autoIntelect ai;

    public AiPlayer(SeaField _gf) {
        ai = new autoIntelect();
        playerType = "Computer";
        gf = _gf;
    }

    @Override
    public TurnResult turn(SeaField _enemyGF) {
        int [] strikeCoord = ai.doStrikeCalculation();
        int x = strikeCoord[0];
        int y = strikeCoord[1];
        CellState strikeEcho = _enemyGF.gotStrike(y,x);
        ai.strikeLearning(strikeCoord,strikeEcho);
        return strikeEcho2TurnResult(strikeEcho);
    }
}

