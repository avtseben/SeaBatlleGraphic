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
    //    for (int i = 0; i < SeaField.FIELD_SIZE; i++)
      //      for (int j = 0; j < SeaField.FIELD_SIZE; j++) {
        //        if (enemiFieldShadow[i][j] == "firedShip") {
          //          if(isShipAlreadyFired(j,i)) {

            //        }
     //               strikeCoordinate[0] = j;
     //               strikeCoordinate[1] = i + 1;
     //               return strikeCoordinate;
             //   }
          //  }
        do {
            strikeCoordinate[0] = MainClass.rand.nextInt(SeaField.FIELD_SIZE);
            strikeCoordinate[1] = MainClass.rand.nextInt(SeaField.FIELD_SIZE);
        } while (cellSplashed(strikeCoordinate[0],strikeCoordinate[1]) || cellFired(strikeCoordinate[0],strikeCoordinate[1]));
            return strikeCoordinate;
    }
    private boolean cellSplashed(int _x, int _y) {
        if(enemiFieldShadow[_y][_x] == "splash") return true;
        return false;
    }
    private boolean cellFired(int _x, int _y) {
        if(enemiFieldShadow[_y][_x] == "firedShip") return true;
        return false;
    }
    public void strikeLearning(int[] strikeCoordinate, String strikeEcho) {
       enemiFieldShadow[strikeCoordinate[1]][strikeCoordinate[0]] = strikeEcho;
    }
    private boolean isShipAlreadyFired(int _x, int _y)
    {
        int iStart = _y - 1;
        int jStart = _x - 1;
        int iStop = _y + 1;
        int jStop = _x + 1;

        //Edge conditions
        if (_x == 0) { jStart = _x+1;}
        //Left Edge
        // *  *
        //x,y *
        // *  *
        else if (_x == SeaField.FIELD_SIZE-1) {jStop = _x-1;}
        if (_y == 0) { iStart = _y+1;}
        else if (_y == SeaField.FIELD_SIZE-1) {iStop = _y-1;}

        for(int i = iStart; i <= iStop; i=i+2) {
            if (enemiFieldShadow[i][_x] == "firedShip") return true;
        for(int j = jStart; j <= jStop; j=j+2) {
            if (enemiFieldShadow[_y][j] != "firedShip") return true;
            }
        }
        return false;
    }
}
