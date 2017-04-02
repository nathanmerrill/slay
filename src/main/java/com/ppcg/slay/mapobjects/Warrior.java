package com.ppcg.slay.mapobjects;

import com.ppcg.slay.Hexagon;
import com.ppcg.slay.SlayMap;
import com.ppcg.slay.Territory;

import java.util.HashSet;
import java.util.Set;

public final class Warrior implements MapObject{
    public final int MAX_DISTANCE = 4;
    private Hexagon location;
    private final int power;
    private boolean dead;
    Warrior(Hexagon location, int power) {
        this.location = location;
        this.power = power;
        dead = false;
        this.location.addListener(SlayMap.MAP_EVENTS.TURN_START, this::turn);
        this.location.addListener(SlayMap.MAP_EVENTS.HEX_DEATH, this::die);
    }

    public Set<Hexagon> getPossibleDestinations(){
        Set<Hexagon> possibleDestinations = new HashSet<>();
        possibleDestinations.add(location);
        Set<Hexagon> frontier = location.getNeighbors();
        Territory currentTerritory = location.getTerritory();
        if (currentTerritory == null){
            return possibleDestinations;
        }
        for (int i = 0; i < MAX_DISTANCE; i++){
            HashSet<Hexagon> currentStep = new HashSet<>(frontier);
            frontier.clear();
            for (Hexagon hex: currentStep){
                if (possibleDestinations.contains(hex)){
                    continue;
                }
                if (currentTerritory.equals(hex.getTerritory())){
                    MapObject.TYPE type = hex.getType();
                    switch (type){
                        case HOUSE: //Can't move onto friendly houses or towers
                        case TOWER:
                            break;
                        case PALM: // Stepping on a grave or tree ends your turn
                        case PINE:
                        case GRAVE:
                            possibleDestinations.add(hex);
                            break;
                        case NOTHING: //If there's nothing there, we can step on it
                            possibleDestinations.add(hex);
                            frontier.addAll(hex.getNeighbors());
                            break;
                    }
                    //Otherwise its a warrior, and we ensure that we can combine the two warriors
                    if (((Warrior)hex.getObject()).power + power > 4){
                        possibleDestinations.add(hex);
                    }
                } else if (hex.getProtectionLevel() < power){
                    possibleDestinations.add(hex);
                }
            }
        }
        return possibleDestinations;
    }

    @Override
    public int getProtection() {
        return power;
    }

    private void turn(Hexagon hexagon) {
        if (dead){
            location.set(TYPE.GRAVE);
        }
    }

    @Override
    public TYPE getType() {
        switch (power){
            case 1:
                return TYPE.PEASANT;
            case 2:
                return TYPE.SPEARMAN;
            case 3:
                return TYPE.KNIGHT;
            case 4:
                return TYPE.BARON;
        }
        return null;
    }

    @Override
    public int getCost() {
        return 2*((int)Math.pow(3, power-1));
    }

    public MapObject die(Hexagon hexagon) {
        dead = true;
        return this;
    }
}
