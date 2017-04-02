package com.ppcg.slay;

import com.ppcg.kothcomm.game.maps.gridmaps.GridMap;
import com.ppcg.slay.mapobjects.MapObject;

public class SlayMap extends GridMap<Hexagon, MapObject> {
    public enum MAP_EVENTS {
        HEX_DEATH,
        ROUND_END,
        TURN_START,
        TURN_END,
    }
    public EventFramework getEventManager(){

    }
}
