package com.ppcg.slay;

import com.ppcg.kothcomm.game.maps.gridmaps.Point2D;
import com.ppcg.slay.mapobjects.MapObject;

import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

public class Hexagon extends Point2D{
    private Territory territory;
    private final SlayMap map;

    public Hexagon(Point2D location, SlayMap map){
        super(location);
        this.territory = null;
        this.map = map;
    }

    public Random getRandom() {
        return map.getRandom();
    }

    public void set(MapObject.TYPE type){
        MapObject object = MapObject.create(type, this);
        if (this.territory != null) {
            territory.updateCharge(getCost() - object.getCost());
        }
        this.map.put(this, object);
    }

    public void setTree(){
        if (isBeach()){
            set(MapObject.TYPE.PALM);
        } else {
            set(MapObject.TYPE.PINE);
        }
    }

    public MapObject.TYPE getType(){
        MapObject object = getObject();
        if (object == null){
            return MapObject.TYPE.NOTHING;
        }
        return object.getType();
    }

    public MapObject getObject(){
        return map.get(this);
    }

    public int getCost(){
        MapObject object = this.map.get(this);
        if (object == null){
            return 0;
        }
        return object.getCost();
    }

    public int getProtectionLevel(){
        if (territory == null){
            return 0;
        }
        return territory.getNeighbors(this).stream()
                .map(Hexagon::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MapObject::getProtection)
                .max()
                .orElse(0);
    }

    public Set<Hexagon> getNeighbors(MapObject.TYPE type){
        Set<Hexagon> connections = map.connections(this);
        connections.removeIf(a -> !type.equals(a.getType()));
        return connections;
    }

    public Set<Hexagon> getNeighbors(){
        return map.connections(this);
    }

    public boolean isBeach() {
        return map.connections(this).size() < 6;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public Territory getTerritory() {
        return territory;
    }

    public void removeListener(SlayMap.MAP_EVENTS event){

    }

    public void addListener(SlayMap.MAP_EVENTS event, Consumer<Hexagon> function){

    }
}
