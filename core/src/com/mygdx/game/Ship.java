package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class Ship {

    private static int shipCount;
    private int id;
    private int size;
    private char dir;
    private int x;
    private int y;
    private CellState [] shipState;
    private Texture sTexture;
    private boolean hited;
    private int textureIndent;
    private Map<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>();

    public Ship (int _x, int _y, char _dir, int _size) {
        id = shipCount;
        shipCount++;
        x = _x;
        y = _y;
        dir = _dir;
        size = _size;
        hited = false;
        textureIndent = 18;
        shipState = new CellState[_size];
        for(int i = 0; i < _size; i++) {
            shipState[i] = CellState.SHIP;
        }
        switch (_size) {
            case 2:
                if(dir == 'V') sTexture = new Texture("2CellVerticalShip.png");
                break;
            default:
                sTexture = new Texture("shipCell.png");
                break;
        }
        loadTextures();
    }
    public void render(SpriteBatch batch, int _leftIndent, int _bottomIndent) {
        if (dir == 'V' && size == 2) {
        if(isKilled())
            batch.draw(textureRegions.get("Killed"),_leftIndent-textureIndent + x*SeaField.CELL_SIZE,_bottomIndent-textureIndent + y*SeaField.CELL_SIZE);
            //batch.draw(sTexture,_leftIndent-textureIndent + x*SeaField.CELL_SIZE,_bottomIndent-textureIndent + y*SeaField.CELL_SIZE,120,0,60,120);
        else if(isHited())
            batch.draw(textureRegions.get("Hited"),_leftIndent-textureIndent + x*SeaField.CELL_SIZE,_bottomIndent-textureIndent + y*SeaField.CELL_SIZE);
            //batch.draw(sTexture,_leftIndent-textureIndent + x*SeaField.CELL_SIZE,_bottomIndent-textureIndent + y*SeaField.CELL_SIZE,60,0,60,120);
        else
            batch.draw(textureRegions.get("NormalShip"),_leftIndent-textureIndent + x*SeaField.CELL_SIZE,_bottomIndent-textureIndent + y*SeaField.CELL_SIZE);
            //batch.draw(sTexture,_leftIndent-textureIndent + x*SeaField.CELL_SIZE,_bottomIndent-textureIndent + y*SeaField.CELL_SIZE,0,0,60,120);
    }
    }
    public void showShipState() {
        for(int i = 0; i < size; i++) {
        }
    }
    public boolean isHited () {
        return hited;
    }
    public boolean isHited (int _x, int _y) {
        switch (this.dir) {
            case 'V':
                if (x == _x && y <= _y && y + size > _y) {
                    shipState[_y - y] = CellState.FIRED;
                    hited = true;
                    return true;
                }
                break;
            case 'H':
                if (y == _y && x <= _x && x + size > _x) {
                    shipState[_x - x] = CellState.FIRED;
                    hited = true;
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
    private void loadTextures() {
        sTexture = new Texture("2CellVerticalShip.png");
        TextureRegion tmp[][] = TextureRegion.split(sTexture,sTexture.getWidth()/3,sTexture.getHeight());
        textureRegions.put("NormalShip", tmp[0][0]);
        textureRegions.put("Hited", tmp[0][1]);
        textureRegions.put("Killed", tmp[0][2]);

    }
}
