package com.mygdx.game;


public class AutoIntelect {

    public CellState[][] enemiField;

    public AutoIntelect() {
        enemiField = new CellState[SeaField.FIELD_SIZE][SeaField.FIELD_SIZE];
        for (int i = 0; i < SeaField.FIELD_SIZE; i++)
            for (int j = 0; j < SeaField.FIELD_SIZE; j++) {
                enemiField[i][j] = CellState.WATER;
            }
    }

    public int[] doStrikeCalculation() {

        int[] strikeCoordinate = new int[2];
        for (int i = 0; i < SeaField.FIELD_SIZE; i++)
            for (int j = 0; j < SeaField.FIELD_SIZE; j++) {
                if (enemiField[i][j] == CellState.NEXT_STRIKE) {
                    strikeCoordinate[0] = j;
                    strikeCoordinate[1] = i;
                    return strikeCoordinate;
                }
           }
        do {
            strikeCoordinate[0] = MainClass.rand.nextInt(SeaField.FIELD_SIZE);
            strikeCoordinate[1] = MainClass.rand.nextInt(SeaField.FIELD_SIZE);
        } while (isCellTurned(strikeCoordinate[0],strikeCoordinate[1]));
            return strikeCoordinate;
    }
    protected boolean isCellTurned(int _x, int _y) {
        if(enemiField[_y][_x] != CellState.WATER) return true;
        return false;
    }
    public void strikeLearning(int[] strikeCoordinate, CellState strikeEcho) {
        int x = strikeCoordinate[0];
        int y = strikeCoordinate[1];
       if(strikeEcho == CellState.FIRED) {
           scanEnemyShip(x,y);
           markDiagonalCells(x,y);
       }
        if(strikeEcho == CellState.SPLASH && enemiField[y][x] == CellState.NEXT_STRIKE) {
            for (int i = 0; i < SeaField.FIELD_SIZE; i++)
                for (int j = 0; j < SeaField.FIELD_SIZE; j++) {
                    if(enemiField[i][j] == CellState.FIRED) {
                        scanEnemyShip(j,i);
                    }
                }
        }
        enemiField[y][x] = strikeEcho;
    }
    protected void markDiagonalCells(int _x, int _y) {
        int iStart = _y - 1;
        int jStart = _x - 1;
        int iStop = _y + 1;
        int jStop = _x + 1;

        //If on Egde position
        if (_x == 0) { jStart = _x+1;}
        else if (_x == SeaField.FIELD_SIZE-1) {jStop = _x-1;}
        if (_y == 0) { iStart = _y+1;}
        else if (_y == SeaField.FIELD_SIZE-1) {iStop = _y-1;}

        markDeadCell(jStart,iStart);
        markDeadCell(jStart,iStop);
        markDeadCell(jStop,iStart);
        markDeadCell(jStop,iStop);
    }

    protected void markDeadCell(int x, int y){
        if(enemiField[y][x] == CellState.WATER)
            enemiField[y][x] = CellState.DEAD;
    }
    protected void markNextStrike(int x, int y){

        enemiField[y][x] = CellState.NEXT_STRIKE;
    }
    protected boolean scanEnemyShip(int _x, int _y)
    {
        int iStart = _y - 1;
        int jStart = _x - 1;
        int iStop = _y + 1;
        int jStop = _x + 1;

        //Edge conditions
        if (_x == 0) { jStart = _x+1;}
        else if (_x == SeaField.FIELD_SIZE-1) {jStop = _x-1;}
        if (_y == 0) { iStart = _y+1;}
        else if (_y == SeaField.FIELD_SIZE-1) {iStop = _y-1;}

        for(int i = iStart; i <= iStop; i=i+2) {
            if (enemiField[i][_x] == CellState.WATER) {
                markNextStrike(_x,i);
                return true;
            }
        }
        for(int j = jStart; j <= jStop; j=j+2) {
            if (enemiField[_y][j] == CellState.WATER) {
                markNextStrike(j,_y);
                return true;
            }
        }
        return false;
    }
    public CellState[][] showYouMind() {
        return enemiField;
    }
}
