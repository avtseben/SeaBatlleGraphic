package com.mygdx.game;
import java.util.Random;

public class AiPlayer extends Player {

    private autoIntelect ai;

    public AiPlayer(SeaField _gf) {
        ai = new autoIntelect();
        playerType = "Computer";
        gf = _gf;
    }
    private Random rand = new Random();

    @Override
    public TurnResult turn(SeaField _enemyGF) {
        int x = rand.nextInt(10);
        int y = rand.nextInt(10);
        return _enemyGF.strike(y, x);
    }
    public int[] doStrike() {
        return ai.doStrikeCalculation();
    }
    public void hearEcho(int[] strikeCoordinate, CellState strikeEcho) {
        ai.strikeLearning(strikeCoordinate,strikeEcho);
    }
}

