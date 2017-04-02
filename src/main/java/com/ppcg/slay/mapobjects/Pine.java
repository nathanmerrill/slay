package com.ppcg.slay.mapobjects;

import com.ppcg.slay.Hexagon;
import com.ppcg.slay.SlayMap;

import java.util.Set;

public final class Pine implements MapObject{
    private final Hexagon location;
    private boolean shouldAct;
    Pine(Hexagon location){
        this.location = location;
        shouldAct = false;
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
        shouldAct = true;
        location.removeListener(SlayMap.MAP_EVENTS.TURN_START);
        location.addListener(SlayMap.MAP_EVENTS.ROUND_END, this::round);
    }

    private void round(Hexagon location) {
        if (!shouldAct){
            return;
        }
        Set<Hexagon> neighbors = location.getNeighbors();
        neighbors.removeIf(hex -> hex.getType() != TYPE.NOTHING);
        neighbors.removeIf(n -> {
            Set<Hexagon> treeNeighbors = n.getNeighbors();
            treeNeighbors.remove(location);
            treeNeighbors.removeIf(hex -> {
               MapObject object = hex.getObject();
                if (object == null){
                    return true;
                }
                if (object.getType() != TYPE.PINE){
                    return true;
                }
                return !((Pine)object).shouldAct;
            });
            return treeNeighbors.isEmpty();
        });
        neighbors.forEach(n -> n.set(TYPE.PINE));
    }

    @Override
    public int getCost() {
        return 1;
    }
}
