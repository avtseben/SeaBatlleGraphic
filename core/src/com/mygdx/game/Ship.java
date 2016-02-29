package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ship {

    private static int shipCount;
    private int id;
    private int size;
    private char dir;
    private int x;
    private int y;
    private CellState [] shipState;
    private Texture sTexture;

    public Ship (int _x, int _y, char _dir, int _size) {
        id = shipCount;
        shipCount++;
        x = _x;
        y = _y;
        dir = _dir;
        size = _size;
        shipState = new CellState[_size];
        for(int i = 0; i < _size; i++) {
            shipState[i] = CellState.SHIP;
        }
        switch (_size) {
            case 2:
                sTexture = new Texture("2CellVerticalShip.png");
                break;
        }
    }
    public void render(SpriteBatch batch, int _leftIndent, int _bottomIndent) {
        batch.draw(sTexture,_leftIndent-15 + x*SeaField.CELL_SIZE,_bottomIndent-15 + y*SeaField.CELL_SIZE,0,0,60,120);
    }
    public void showShipState() {
        //System.out.println("Ship№ " + id + " Size: " + size);
        for(int i = 0; i < size; i++) {
          //  System.out.print("" + shipState[i]);
        }
        //System.out.println("<____________>");
    }
    public boolean isHited (int _x, int _y) {
        switch (this.dir) {
            case 'V':
                if (x == _x && y <= _y && y + size > _y) {
                    shipState[_y - y] = CellState.FIRED;
          //          System.out.print("Fired otsek = " + (_y - y));
                    return true;
                }
                break;
            case 'H':
                if (y == _y && x <= _x && x + size > _x) {
                    shipState[_x - x] = CellState.FIRED;
            //        System.out.print("Fired otsek = " + (_x - x));
                    return true;
                }
                break;
        }
        return false;
    }
    public int getId() {
        return id;
    }
    public boolean isKilled () {
        boolean aLive = false;
        for(int i = 0; i < size; i++)
            if(shipState[i] == CellState.SHIP)
                aLive = true;
        if(!aLive)
            for(int i = 0; i < size; i++) {
                shipState[i] = CellState.KILLED;
            }
        return !aLive;
    }
    public char getDir () {
        return dir;
    }

    public int getSize() {
        return size;
    }
}
