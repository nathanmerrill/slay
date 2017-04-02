package com.ppcg.slay.mapobjects;

import com.ppcg.slay.Hexagon;

public final class Tower implements MapObject{

    Tower(Hexagon hexagon){

    }
    @Override
    public int getProtection() {
        return 2;
    }


    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public TYPE getType() {
        return TYPE.TOWER;
    }
}
