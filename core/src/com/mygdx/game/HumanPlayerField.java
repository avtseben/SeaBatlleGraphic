package com.mygdx.game;

public class HumanPlayerField extends SeaField {

    private boolean testSetAllShip = true;

    public HumanPlayerField(int x, int y) {
        super(x, y);
        fieldSet = new String[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++) {
                fieldSet[i][j] = WATER_CELL;
            }
        setAllShipOnField();
        playerType = "Human";
        isMine = true;
    }
}
