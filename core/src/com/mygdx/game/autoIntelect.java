package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class autoIntelect {

    private String[][] enemiFieldShadow;
    private CellState[][] enemiField;
    private BitmapFont fnt;

    public autoIntelect() {
        enemiField = new CellState[SeaField.FIELD_SIZE][SeaField.FIELD_SIZE];
        for (int i = 0; i < SeaField.FIELD_SIZE; i++)
            for (int j = 0; j < SeaField.FIELD_SIZE; j++) {
                enemiField[i][j] = CellState.WATER;
            }
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);

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
        } while (cellSplashed(strikeCoordinate[0],strikeCoordinate[1]) || cellIsDead(strikeCoordinate[0],strikeCoordinate[1]) || cellFired(strikeCoordinate[0],strikeCoordinate[1]));
            return strikeCoordinate;
    }
    private boolean cellIsDead(int _x, int _y) {
        if(enemiField[_y][_x] == CellState.DEAD) return true;
        return false;
    }
    private boolean cellSplashed(int _x, int _y) {
        if(enemiField[_y][_x] == CellState.SPLASH) return true;
        return false;
    }
    private boolean cellFired(int _x, int _y) {
        if(enemiField[_y][_x] == CellState.FIRED) return true;
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
    private void markDiagonalCells(int _x, int _y) {
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

    private void markDeadCell(int x, int y){
        enemiField[y][x] = CellState.DEAD;
    }
    private void markNextStrike(int x, int y){

        enemiField[y][x] = CellState.NEXT_STRIKE;
    }
    private boolean scanEnemyShip(int _x, int _y)
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
                System.out.println(enemiField[i][_x]);
                System.out.println("coord:" + _x + i);
                return true;
            }
        }
        for(int j = jStart; j <= jStop; j=j+2) {
            if (enemiField[_y][j] == CellState.WATER) {
                markNextStrike(j,_y);
                System.out.println(enemiField[_y][j]);
                System.out.println("coord: " + j + _y);
                return true;
            }
        }
        return false;
    }
    public CellState[][] showYouMind() {
        return enemiField;
    }
}
