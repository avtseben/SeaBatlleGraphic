package com.mygdx.game;

public abstract class Player {

    protected SeaField gf;
    protected String playerType;

    public SeaField getField() {
        return gf;
    }
    public abstract TurnResult turn(SeaField _enemyGF);
    public TurnResult strikeEcho2TurnResult (CellState cs) {
        switch (cs) {
            case SPLASH:
                return TurnResult.Miss;
            case FIRED:
                return TurnResult.Hit;
            case KILLED:
                return TurnResult.Kill;
        }
        return TurnResult.Wait;
    }
}
