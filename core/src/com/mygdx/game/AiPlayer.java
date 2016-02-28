package com.mygdx.game;
import java.util.Random;

public class AiPlayer extends Player {

    public AiPlayer(SeaField _gf) {
        gf = _gf;
    }
    private Random rand = new Random();

    @Override
    public TurnResult turn(SeaField _enemyGF) {
        int x = rand.nextInt(10);
        int y = rand.nextInt(10);
        return _enemyGF.strike(y, x);
    }
}

