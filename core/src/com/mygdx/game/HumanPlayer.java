package com.mygdx.game;

public class HumanPlayer extends Player {

    public HumanPlayer(SeaField _gf) {
        gf = _gf;
        playerType = "Human";
        //isMine = true;
    }

    @Override
    public TurnResult turn(SeaField _enemyGF) {
        if (InputHandler.isClicked())
            return _enemyGF.strike(_enemyGF.getMouseCellY(), _enemyGF.getMouseCellX());
        return TurnResult.Wait;
    }
}

