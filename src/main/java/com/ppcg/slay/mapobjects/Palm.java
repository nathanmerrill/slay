package com.ppcg.slay.mapobjects;

import com.ppcg.slay.Hexagon;
import com.ppcg.slay.SlayMap;

import java.util.Set;

public final class Palm implements MapObject{
    Palm(Hexagon location){
        location.addListener(SlayMap.MAP_EVENTS.TURN_START, this::turn);
    }
    @Override
    public int getProtection() {
        return 0;
    }

    @Override
    public TYPE getType() {
        return TYPE.PALM;
    }

    private void turn(Hexagon location) {
        location.removeListener(SlayMap.MAP_EVENTS.TURN_START);
        location.addListener(SlayMap.MAP_EVENTS.ROUND_END, this::round);
    }

    private void round(Hexagon location) {
        Set<Hexagon> neighbors = location.getNeighbors();
        neighbors.removeIf(n -> n.getObject() != null);
        neighbors.removeIf(n -> !n.isBeach());
        if (neighbors.size() == 0){
            return;
        }
        neighbors.forEach(n -> n.set(TYPE.PALM));
    }

    @Override
    public int getCost() {
        return 1;
    }
}
