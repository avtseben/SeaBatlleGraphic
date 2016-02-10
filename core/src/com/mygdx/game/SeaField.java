package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
public abstract class SeaField {

	final static int FIELD_SIZE = 10;
	final static int CELL_SIZE = 30;
    int FIELD_SIZE_PIXELS = FIELD_SIZE * CELL_SIZE;
    protected BitmapFont fnt;
    protected String[][] fieldSet;
    protected int x;
    protected int y;
    protected int selCellX;
	protected int selCellY;
	protected static Texture cellsTexture;
    protected static Texture seaTexture;
    protected static Texture splashTexture;
    protected static Texture aimTexture;
    protected String playerType;
    protected int shipSet[] = {4,3,3,2,2,2,1,1,1,1}; //4-Однопалубных 3-двухпалубных 2-трех палубных и 1 -четырехпалубный
    protected boolean isMine = false;

	public SeaField(int x, int y) {
		this.x = x;
        this.y = y;
        fieldSet = new String[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++){
            fieldSet[i][j] = "water";
        }
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
        aimTexture = new Texture("pointer.tga");
        cellsTexture = new Texture("Cells.png");
        splashTexture = new Texture("splashCell.png");
        seaTexture = new Texture("FullWater.png");
    }
    public void render(SpriteBatch batch) {
        update();
        batch.draw(seaTexture,x-112,y-44);
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (fieldSet[i][j] == "ship" && fieldIsMy())
                    batch.draw(cellsTexture, x + j * CELL_SIZE, y + i * CELL_SIZE, 0, 0, 30, 30);//Данный перегруженнный метод draw позволяет отрисовать часть картинки
                if (fieldSet[i][j] == "firedShip")
                    batch.draw(cellsTexture, x + j * CELL_SIZE, y + i * CELL_SIZE, 30, 0, 30, 30);//Данный перегруженнный метод draw позволяет отрисовать часть картинки
                if (fieldSet[i][j] == "splash")
                    batch.draw(splashTexture, x + j * CELL_SIZE, y + i * CELL_SIZE);
            }
        }
        if(selCellX > -1 && selCellY > -1)
            batch.draw(aimTexture, x + selCellX * CELL_SIZE, y + selCellY * CELL_SIZE);
    }
    public void update() {
        selCellX = (InputHandler.getMouseX() - x) / CELL_SIZE;
        selCellY = (InputHandler.getMouseY() - y) / CELL_SIZE;
        if(InputHandler.getMouseX() < x || InputHandler.getMouseY() < y || InputHandler.getMouseX() >= x + FIELD_SIZE_PIXELS || InputHandler.getMouseY() >= y + FIELD_SIZE_PIXELS) {
            selCellX = -1;
            selCellY = -1;
        }
    }

    public boolean clickForStrike() {
        if(InputHandler.isClicked() && selCellY > -1 && selCellX > -1 && !fieldIsMy())
            if(fieldSet[selCellY][selCellX] == "water" || fieldSet[selCellY][selCellX] == "ship") {
                gotStrike(selCellX,selCellY);
                return true;
        }
        return false;
    }

    public String gotStrike(int x, int y) {
        if (fieldSet[y][x] == "ship") {
            fieldSet[y][x] = "firedShip";
            return fieldSet[y][x];
        }
        if (fieldSet[y][x] == "water") {
            fieldSet[y][x] = "splash";
            return fieldSet[y][x];
        }
        return fieldSet[y][x];
    }
    public boolean fieldIsMy() {
        return isMine;
    }
    public String[][] showFieldSet() {
        return fieldSet;
    }
}
