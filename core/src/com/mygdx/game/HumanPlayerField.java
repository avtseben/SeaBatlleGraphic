package com.mygdx.game;

public class HumanPlayerField extends SeaField {

    private boolean testSetAllShip = true;

    public HumanPlayerField(int x, int y) {
        super(x, y);
        playerType = "Human";
        isMine = true;
    }
}
