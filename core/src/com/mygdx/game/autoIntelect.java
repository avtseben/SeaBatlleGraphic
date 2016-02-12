package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class autoIntelect {

    private String[][] enemiFieldShadow;
    private BitmapFont fnt;

    public autoIntelect() {
        enemiFieldShadow = new String[SeaField.FIELD_SIZE][SeaField.FIELD_SIZE];
        for (int i = 0; i < SeaField.FIELD_SIZE; i++)
            for (int j = 0; j < SeaField.FIELD_SIZE; j++) {
                enemiFieldShadow[i][j] = SeaField.WATER_CELL;
            }
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);

    }

    public int[] doStrikeCalculation() {

        int[] strikeCoordinate = new int[2];
        for (int i = 0; i < SeaField.FIELD_SIZE; i++)
            for (int j = 0; j < SeaField.FIELD_SIZE; j++) {
                if (enemiFieldShadow[i][j] == SeaField.NEXT_STRIKE_CELL) {
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
        if(enemiFieldShadow[_y][_x] == SeaField.DEAD_CELL) return true;
        return false;
    }
    private boolean cellSplashed(int _x, int _y) {
        if(enemiFieldShadow[_y][_x] == SeaField.SPLASH_CELL) return true;
        return false;
    }
    private boolean cellFired(int _x, int _y) {
        if(enemiFieldShadow[_y][_x] == SeaField.FIRED_SHIP_CELL) return true;
        return false;
    }
    public void strikeLearning(int[] strikeCoordinate, String strikeEcho) {
        int x = strikeCoordinate[0];
        int y = strikeCoordinate[1];
       if(strikeEcho == SeaField.FIRED_SHIP_CELL) {
           scanEnemyShip(x,y);
           markDiagonalCells(x,y);
       }
        if(strikeEcho == SeaField.SPLASH_CELL && enemiFieldShadow[y][x] == SeaField.NEXT_STRIKE_CELL) {
            for (int i = 0; i < SeaField.FIELD_SIZE; i++)
                for (int j = 0; j < SeaField.FIELD_SIZE; j++) {
                    if(enemiFieldShadow[i][j] == SeaField.FIRED_SHIP_CELL) {
                        scanEnemyShip(j,i);
                    }
                }
        }
        enemiFieldShadow[y][x] = strikeEcho;
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
        enemiFieldShadow[y][x] = SeaField.DEAD_CELL;
    }
    private void markNextStrike(int x, int y){

        enemiFieldShadow[y][x] = SeaField.NEXT_STRIKE_CELL;
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
            if (enemiFieldShadow[i][_x] == SeaField.WATER_CELL) {
                markNextStrike(_x,i);
                System.out.println(enemiFieldShadow[i][_x]);
                System.out.println("coord:" + _x + i);
                return true;
            }
        }
        for(int j = jStart; j <= jStop; j=j+2) {
            if (enemiFieldShadow[_y][j] == SeaField.WATER_CELL) {
                markNextStrike(j,_y);
                System.out.println(enemiFieldShadow[_y][j]);
                System.out.println("coord: " + j + _y);
                return true;
            }
        }
        return false;
    }
    public String[][] showYouMind() {
        return enemiFieldShadow;
    }
}
