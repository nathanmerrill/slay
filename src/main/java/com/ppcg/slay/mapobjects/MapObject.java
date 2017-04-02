package com.ppcg.slay.mapobjects;

import com.ppcg.slay.Hexagon;

import java.util.function.Function;

public interface MapObject {
    enum TYPE {
        PEASANT(h -> new Warrior(h, 1)),
        SPEARMAN(h -> new Warrior(h, 2)),
        KNIGHT(h -> new Warrior(h, 3)),
        BARON(h -> new Warrior(h, 4)),
        TOWER(Tower::new),
        HOUSE(House::new),
        PALM(Palm::new),
        PINE(Pine::new),
        GRAVE(Grave::new),
        NOTHING(h -> null);
        private final Function<Hexagon, MapObject> constructor;
        TYPE(Function<Hexagon, MapObject> constructor){
            this.constructor = constructor;
        }
    }
    static MapObject create(MapObject.TYPE type, Hexagon hexagon){
        return type.constructor.apply(hexagon);
    }
    int getProtection();
    TYPE getType();
    int getCost();
}
