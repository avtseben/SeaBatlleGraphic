package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class SeaField {

    final static int FIELD_SIZE = 10;
    final static int CELL_SIZE = 30;
    int FIELD_SIZE_PIXELS = FIELD_SIZE * CELL_SIZE;
    protected BitmapFont fnt;
    protected CellState[][] fieldStateSet;
    protected int x;
    protected int y;
    protected int selCellX;
    protected int selCellY;
    protected static Texture shipTexture;
    protected static Texture firedTexture;
    protected Texture seaTexture;
    protected Texture seaTexture2;
    protected static Texture splashTexture;
    protected static Texture aimTexture;
    protected int shipSet[] = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1}; //4-Однопалубных 3-двухпалубных 2-трех палубных и 1 -четырехпалубный
    protected boolean testSetAllShip = false;
    private ArrayList<Ship> ships = new ArrayList<Ship>();

    public SeaField(int x, int y) {
        this.x = x;
        this.y = y;
        fieldStateSet = new CellState[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++) {
                fieldStateSet[i][j] = CellState.WATER;
            }
        setAllShipOnField();
        //showShips();
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
        aimTexture = new Texture("pointer.tga");
        shipTexture = new Texture("shipCell.png");
        firedTexture = new Texture("firedCell.png");
        splashTexture = new Texture("splashCell.png");
        seaTexture = new Texture("FullWater.png");
        seaTexture2 = new Texture("FullWater_FR.png");
    }
    public void showShips() {
        for(Ship s : ships) {
            s.showShipState();
        }
    }
    public void render(SpriteBatch batch, boolean visibleShip, int instance) {//TODO game field beter to separate from player and this render should take bolean visible variable for rendering enemi ship
        update();
        switch (instance) {
            case 1:
                batch.draw(seaTexture, x - 112, y - 44);
                break;
            case 2:
                batch.draw(seaTexture2, x - 112, y - 44);
                break;
        }
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (fieldStateSet[i][j] == CellState.SHIP && visibleShip)
                    batch.draw(shipTexture, x + j * CELL_SIZE - 30, y + i * CELL_SIZE);//TODO эти магические цыфры я не трогаю
                if (fieldStateSet[i][j] == CellState.FIRED)                             //потому что этот блок уйдет
                    batch.draw(firedTexture, x + j * CELL_SIZE - 30, y + i * CELL_SIZE);//будут отрисовываться только обекты
                if (fieldStateSet[i][j] == CellState.KILLED)
                    batch.draw(firedTexture, x + j * CELL_SIZE - 30, y + i * CELL_SIZE);
                if (fieldStateSet[i][j] == CellState.SPLASH)
                    batch.draw(splashTexture, x + j * CELL_SIZE, y + i * CELL_SIZE);
            }
        }
        if (selCellX > -1 && selCellY > -1)
            batch.draw(aimTexture, x + selCellX * CELL_SIZE, y + selCellY * CELL_SIZE);
        if(visibleShip) {
            for (Ship s : ships) {
                    s.render(batch, x, y);
            }
        }

    }
    public void update() {
        selCellX = (InputHandler.getMouseX() - x) / CELL_SIZE;
        selCellY = (InputHandler.getMouseY() - y) / CELL_SIZE;
        if (InputHandler.getMouseX() < x || InputHandler.getMouseY() < y || InputHandler.getMouseX() >= x + FIELD_SIZE_PIXELS || InputHandler.getMouseY() >= y + FIELD_SIZE_PIXELS) {
            selCellX = -1;
            selCellY = -1;
        }
    }
    public boolean setAllShipOnField() {
        char tempDir = 'H';
        if (testSetAllShip && testSetAllShipOnField())
            return true;

        for(int i = 0;i<shipSet.length;i++) {
            if (MainClass.rand.nextInt(2) == 0)
                tempDir = 'V';
            else
                tempDir = 'H';
            int x, y;
            do {//Потенциально бесконечный цыкл. Если комп расставит корабли так что не сможет поставить 5-й цикл будет без конца
                x = MainClass.rand.nextInt(FIELD_SIZE);
                y = MainClass.rand.nextInt(FIELD_SIZE);
            } while (!setOneShip(x, y, tempDir, shipSet[i]));
        }
        return false;
    }
    private boolean testSetAllShipOnField() {
        /*
        * 1 0 1 0 1 0 1 0 1 0
        * 0 0 0 0 0 0 0 0 0 0
        * 1 1 0 1 1 0 1 1 0 0
        * 0 0 0 0 0 0 0 0 0 0
        * 1 1 1 0 1 1 1 0 0 0
        * 0 0 0 0 0 0 0 0 0 0
        * 0 0 0 0 0 0 0 0 0 0
        * 0 0 0 0 0 0 0 0 0 0
        * 0 0 0 0 0 0 0 0 0 0
        * 0 0 0 0 0 1 1 1 1 1
        * */
        setOneShip(2,8,'V',2);
        setOneShip(7,8,'H',1);
        setOneShip(6,0,'H',4);
        return true;
    }
    private boolean setOneShip(int _x, int _y, char _dir, int _size)
    {
        int vx = 0, vy = 0;
        if (_dir == 'H')
            vx = 1;//Если H - Horizontal значит приращение по x
        else
            vy = 1;
        if(_x + _size * vx > FIELD_SIZE) return false; //Если корабль заезжает за край поля
        if(_y + _size * vy > FIELD_SIZE) return false;// прайнее поле 9 - ячейка массива

        for (int i = 0; i<_size;i++)
            if (!freeWater(_x + i * vx,_y + i * vy)) return false;//Если корабль уже стоит возращаем ложь

        for (int i = 0; i<_size;i++) {
            fieldStateSet[_y + i * vy][_x + i * vx] = CellState.SHIP;//Ставим корабль     2
        }
        if(testSetAllShip) {
            System.out.println("_x= " + _x  + "_vx*size= " + _size*vx);
            System.out.println("_y= " + _y  + "_vy*size= " + _size*vy);
        }
        ships.add(new Ship(_x,_y,_dir,_size));
        return true;
    }
    private boolean freeWater(int _x, int _y)//Проверка на наличие свободной воды в радиусе одной клетки
    {
        int iStart = _y - 1;
        int jStart = _x - 1;
        int iStop = _y + 1;
        int jStop = _x + 1;

        //Edge conditions
        if (_x == 0) { jStart = _x;}
        else if (_x == FIELD_SIZE-1) {jStop = _x;}
        if (_y == 0) { iStart = _y;}
        else if (_y == FIELD_SIZE-1) {iStop = _y;}

        for(int i = iStart; i <= iStop; i++) {
            for (int j = jStart; j <= jStop; j++) {
                if (fieldStateSet[i][j] != CellState.WATER) return false;//Если находим кусок корабля значит ложь
            }
        }
        return true;
    }
    public int getMouseCellX() {
        int selCellX = (InputHandler.getMouseX() - x) / CELL_SIZE;
        if (InputHandler.getMouseX() < x || InputHandler.getMouseX() >= x + FIELD_SIZE_PIXELS)
            selCellX = -1;
        return selCellX;
    }

    public int getMouseCellY() {
        int selCellY = (InputHandler.getMouseY() - y) / CELL_SIZE;
        if (InputHandler.getMouseY() < y || InputHandler.getMouseY() >= y + FIELD_SIZE_PIXELS)
            selCellY = -1;
        return selCellY;
    }
    public CellState gotStrike(int x, int y) {
        if (fieldStateSet[y][x] == CellState.SHIP) {
            for(Ship s : ships) {
                if(s.isHited(x,y))
                    if(s.isKilled()) {
                        fieldStateSet[y][x] = CellState.KILLED;
                        System.out.println("" + CellState.KILLED);
                        s.showShipState();
                        return fieldStateSet[y][x];
                    } else {
                        fieldStateSet[y][x] = CellState.FIRED;
                        System.out.println("" + CellState.FIRED);
                        s.showShipState();
                        return fieldStateSet[y][x];
                    }
            }
        }
        if (fieldStateSet[y][x] == CellState.WATER) {
            fieldStateSet[y][x] = CellState.SPLASH;
            System.out.println("" + CellState.SPLASH);
            return fieldStateSet[y][x];
        }
        return fieldStateSet[y][x];
    }
    public boolean isDefeated() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (fieldStateSet[i][j] == CellState.SHIP) return false;
            }
        }
        return true;
    }

}
