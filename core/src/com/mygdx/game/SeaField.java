package com.mygdx.game;
//
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public abstract class SeaField {

    final static int FIELD_SIZE = 10;
    final static int CELL_SIZE = 30;
    int FIELD_SIZE_PIXELS = FIELD_SIZE * CELL_SIZE;
    protected BitmapFont fnt;
    protected CellState[][] fieldStateSet;
    protected int x;
    protected int y;
    protected int selCellX;
    protected int selCellY;
    protected static Texture cellsTexture;
    protected Texture seaTexture;
    protected static Texture splashTexture;
    protected static Texture aimTexture;
    protected String playerType;
    protected int shipSet[] = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1}; //4-Однопалубных 3-двухпалубных 2-трех палубных и 1 -четырехпалубный
    protected boolean isMine = false;
    protected boolean testSetAllShip = false;


    public SeaField(int x, int y) {
        this.x = x;
        this.y = y;
        fieldStateSet = new CellState[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++) {
                fieldStateSet[i][j] = CellState.WATER;
            }
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
        aimTexture = new Texture("pointer.tga");
        cellsTexture = new Texture("Cells.png");
        splashTexture = new Texture("splashCell.png");
        seaTexture = new Texture("FullWater.png");
    }

    public void render(SpriteBatch batch) {//TODO game field beter to separate from player and this render should take bolean visible variable for rendering enemi ship
        update();
        batch.draw(seaTexture, x - 112, y - 44);
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (fieldStateSet[i][j] == CellState.SHIP && fieldIsMy())
                    batch.draw(cellsTexture, x + j * CELL_SIZE, y + i * CELL_SIZE, 0, 0, 30, 30);//Данный перегруженнный метод draw позволяет отрисовать часть картинки
                if (fieldStateSet[i][j] == CellState.FIRED)
                    batch.draw(cellsTexture, x + j * CELL_SIZE, y + i * CELL_SIZE, 30, 0, 30, 30);//Данный перегруженнный метод draw позволяет отрисовать часть картинки
                if (fieldStateSet[i][j] == CellState.SPLASH)
                    batch.draw(splashTexture, x + j * CELL_SIZE, y + i * CELL_SIZE);
            }
        }
        if (selCellX > -1 && selCellY > -1)
            batch.draw(aimTexture, x + selCellX * CELL_SIZE, y + selCellY * CELL_SIZE);
    }

    public void update() {
        selCellX = (InputHandler.getMouseX() - x) / CELL_SIZE;
        selCellY = (InputHandler.getMouseY() - y) / CELL_SIZE;
        if (InputHandler.getMouseX() < x || InputHandler.getMouseY() < y || InputHandler.getMouseX() >= x + FIELD_SIZE_PIXELS || InputHandler.getMouseY() >= y + FIELD_SIZE_PIXELS) {
            selCellX = -1;
            selCellY = -1;
        }
    }

    public boolean clickForStrike() {
        if (InputHandler.isClicked() && selCellY > -1 && selCellX > -1 && !fieldIsMy())
            if (fieldStateSet[selCellY][selCellX] == CellState.WATER || fieldStateSet[selCellY][selCellX] == CellState.SHIP) {
                gotStrike(selCellX, selCellY);
                return true;
            }
        return false;
    }

    public CellState gotStrike(int x, int y) {
        if (fieldStateSet[y][x] == CellState.SHIP) {
            fieldStateSet[y][x] = CellState.FIRED;
            return fieldStateSet[y][x];
        }
        if (fieldStateSet[y][x] == CellState.WATER) {
            fieldStateSet[y][x] = CellState.SPLASH;
            return fieldStateSet[y][x];
        }
        return fieldStateSet[y][x];
    }

    public boolean fieldIsMy() {
        return isMine;
    }

    public boolean haveAliveShips() {
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++)
                if (fieldStateSet[i][j] == CellState.SHIP) return true;
        return false;
    }
    public boolean setAllShipOnField() {
        char tempDir = 'H';
        if (testSetAllShip && testSetAllShipOnField())
            return true;

        for(int i = 0;i<shipSet.length;i++) {
            if (MainClass.rand.nextInt(2) == 0) ///Бросаем кубик либо 0 либо 1, если 0 - строим вертикально
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
}
