package com.ppcg.slay;


import com.ppcg.kothcomm.game.AbstractGame;
import com.ppcg.kothcomm.game.scoreboards.AggregateScoreboard;

public class Slay extends AbstractGame<Player> {

    @Override
    public void setup() {
        SlayMap map = new SlayMap();

    }

    @Override
    public AggregateScoreboard<Player> getScores() {
        return null;
    }

    @Override
    protected boolean step() {
        return false;
    }
}
