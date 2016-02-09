package com.mygdx.game;

public class HumanPlayerField extends SeaField {

    private boolean testSetAllShip = false;

    public HumanPlayerField(int x, int y) {
        super(x, y);
        fieldSet = new String[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++)
            for (int j = 0; j < FIELD_SIZE; j++){
                fieldSet[i][j] = "water";
            }
        setAllShipOnField();
        playerType = "Human";
    }
    public boolean setAllShipOnField() {
        char tempDir = 'H';
        if (testSetAllShip && testSetAllShipOnField())
            return true;

        for(int i = 0;i<shipSet.length;i++) {
            if (MainClass.rand.nextInt(2) == 0) //Бросаем кубик либо 0 либо 1, если 0 - строим вертикально
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
    private boolean setOneShip(int _x, int _y, char _dir, int _size)//TODO _number unneeded
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
            fieldSet[_y + i * vy][_x + i * vx] = "ship";//Ставим корабль     2
            //   shipMarker[_y + i * vy][_x + i * vx] = _number;
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
        //Left Edge
        // *  *
        //x,y *
        // *  *
        else if (_x == FIELD_SIZE-1) {jStop = _x;}
        if (_y == 0) { iStart = _y;}
        else if (_y == FIELD_SIZE-1) {iStop = _y;}

        for(int i = iStart; i <= iStop; i++) {
            for (int j = jStart; j <= jStop; j++) {
                if (fieldSet[i][j] != "water") return false;//Если находим кусок корабля значит ложь
            }
        }
        return true;
    }

}

