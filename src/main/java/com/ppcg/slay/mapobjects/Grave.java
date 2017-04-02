package com.ppcg.slay.mapobjects;

import com.ppcg.slay.Hexagon;
import com.ppcg.slay.SlayMap;

public final class Grave implements MapObject{
    Grave(Hexagon location){
        location.addListener(SlayMap.MAP_EVENTS.TURN_START, this::turn);
    }

    @Override
    public int getProtection() {
        return 0;
    }

    private void turn(Hexagon location) {
        location.setTree();
    }

    @Override
    public TYPE getType() {
        return TYPE.GRAVE;
    }

    @Override
    public int getCost() {
        return 0;
    }
}
