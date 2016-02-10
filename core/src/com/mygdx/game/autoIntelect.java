package com.mygdx.game;

public class autoIntelect {

    private String[][] enemiFieldShadow;

    public autoIntelect() {
        enemiFieldShadow = new String[SeaField.FIELD_SIZE][SeaField.FIELD_SIZE];
        for (int i = 0; i < SeaField.FIELD_SIZE; i++)
            for (int j = 0; j < SeaField.FIELD_SIZE; j++) {
                enemiFieldShadow[i][j] = "water";
            }
    }

    public int[] doStrikeCalculation() {

        int[] strikeCoordinate = new int[2];
        for (int i = 0; i < SeaField.FIELD_SIZE; i++)
            for (int j = 0; j < SeaField.FIELD_SIZE; j++) {
                if (enemiFieldShadow[i][j] == "firedShip") {
                    strikeCoordinate[0] = j;
                    strikeCoordinate[1] = i + 1;
                    return strikeCoordinate;
                }
            }
        strikeCoordinate[0] = MainClass.rand.nextInt(SeaField.FIELD_SIZE);
        strikeCoordinate[1] = MainClass.rand.nextInt(SeaField.FIELD_SIZE);
        return strikeCoordinate;
    }
    public void strikeLearning(int[] strikeCoordinate, String strikeEcho) {
       enemiFieldShadow[strikeCoordinate[1]][strikeCoordinate[0]] = strikeEcho;
    }
}
