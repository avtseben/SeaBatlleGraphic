package com.mygdx.game;

public class SmartIntelect extends AutoIntelect {

   public void strikeLearning(int[] strikeCoordinate, CellState strikeEcho) {
      int x = strikeCoordinate[0];
      int y = strikeCoordinate[1];
      if(strikeEcho == CellState.FIRED) {
         scanEnemyShip(x,y);
         markDiagonalCells(x,y);
      }
      //Убил корабль больше одной палубы
      else if(strikeEcho == CellState.KILLED && enemiField[y][x] == CellState.NEXT_STRIKE) {
          enemiField[y][x] = CellState.KILLED;
           for (int i = 0; i < SeaField.FIELD_SIZE; i++)
               for (int j = 0; j < SeaField.FIELD_SIZE; j++) {
                   if(enemiField[i][j] == CellState.FIRED || enemiField[i][j] == CellState.KILLED) {
                       markAreaAfterKill(j,i);
                       markDiagonalCells(j,i);
                       System.out.println("---More than onr");
                   }
               }
       }
       //Убил однопалубный
       else if(strikeEcho == CellState.KILLED) {
          enemiField[y][x] = CellState.KILLED;
           markAreaAfterKill(x,y);
           markDiagonalCells(x,y);
      }
       //Попал в многопалубный(еще не убил)
      else if(strikeEcho == CellState.SPLASH && enemiField[y][x] == CellState.NEXT_STRIKE) {
         for (int i = 0; i < SeaField.FIELD_SIZE; i++)
            for (int j = 0; j < SeaField.FIELD_SIZE; j++) {
               if(enemiField[i][j] == CellState.FIRED) {
                  scanEnemyShip(j,i);
               }
            }
      }
      enemiField[y][x] = strikeEcho;
   }
  public void markAreaAfterKill(int _x, int _y) {
      int iStart = _y - 1;
      int jStart = _x - 1;
      int iStop = _y + 1;
      int jStop = _x + 1;

      //Edge conditions
      if (_x == 0) {
          jStart = _x + 1;
      } else if (_x == SeaField.FIELD_SIZE - 1) {
          jStop = _x - 1;
      }
      if (_y == 0) {
          iStart = _y + 1;
      } else if (_y == SeaField.FIELD_SIZE - 1) {
          iStop = _y - 1;
      }

      for (int i = iStart; i <= iStop; i++) {
          if (enemiField[i][_x] == CellState.WATER) {
              markDeadCell(_x, i);
          }
      }
      for (int j = jStart; j <= jStop; j++) {
          if (enemiField[_y][j] == CellState.WATER) {
              markDeadCell(j, _y);
          }
      }
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


}
