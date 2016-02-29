package com.mygdx.game;

public class HumanPlayer extends Player {

    public HumanPlayer(SeaField _gf) {
        gf = _gf;
        playerType = "Human";
    }

    @Override
    public TurnResult turn(SeaField _enemyGF) {
        if (InputHandler.isClicked())
            return strikeEcho2TurnResult(_enemyGF.gotStrike(_enemyGF.getMouseCellY(), _enemyGF.getMouseCellX()));
        return TurnResult.Wait;
    }
}

