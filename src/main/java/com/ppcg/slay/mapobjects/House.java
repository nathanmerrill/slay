package com.ppcg.slay.mapobjects;

import com.ppcg.slay.Hexagon;
import com.ppcg.slay.SlayMap;

public final class House implements MapObject{
    House(Hexagon location){
        location.addListener(SlayMap.MAP_EVENTS.HEX_DEATH, this::die);
    }
    @Override
    public int getProtection() {
        return 1;
    }

    @Override
    public TYPE getType() {
        return null;
    }

    private void die(Hexagon hexagon) {
        hexagon.setTree();
    }

    @Override
    public int getCost() {
        return 0;
    }
}
