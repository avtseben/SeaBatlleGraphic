package com.mygdx.game;

public abstract class Player {

    protected SeaField gf;
    protected String playerType;
    //protected boolean isMine = false;

    public SeaField getField() {
        return gf;
    }
    public abstract TurnResult turn(SeaField _enemyGF);
}
