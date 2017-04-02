package com.ppcg.slay;

import com.ppcg.kothcomm.game.maps.GameMap;
import com.ppcg.kothcomm.utils.Tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Territory {
    private final Owner owner;
    private final Set<Hexagon> lands;
    private Hexagon houseLocation;
    private final SlayMap map;
    private int reserves;
    private int income;
    Territory(SlayMap map, Owner owner, Set<Hexagon> lands){
        this.owner = owner;
        owner.addTerritory(this);
        this.map = map;
        this.lands = lands;
        this.reserves = 0;
        setHouse();
        recalculateIncome();
    }

    private void setHouse(){
        this.houseLocation = new ArrayList<>(lands).get(map.getRandom().nextInt(lands.size()));
    }

    private void remove(Hexagon hexagon){
        lands.remove(hexagon);
        if (size() == 1){
            owner.removeTerritory(this);
        }
        List<Set<Hexagon>> regions = getSeperateRegions(hexagon);
        if (regions.size() == 1){
            income -= 1 - hexagon.getCost();
            return;
        }
        List<Territory> territories = new ArrayList<>();
        for (Set<Hexagon> region: regions){
            if (region.size() == 1){
                Hexagon hex = region.stream().findAny().get();
                map.getEventManager().fire(SlayMap.MAP_EVENTS.HEX_DEATH, hex);
            }
            if (region.contains(houseLocation)){
                lands.retainAll(region);
                territories.add(this);
            } else {
                if (region.size() > 1) {
                    territories.add(new Territory(map, owner, region));
                }
            }
        }
        int reserves = this.reserves;
        this.reserves = 0;
        int largestSize = territories.stream().mapToInt(Territory::size).max().getAsInt();
        Territory largest = Tools.sample(
                territories.stream()
                        .filter(t -> t.size() == largestSize)
                        .collect(Collectors.toList()),
                map.getRandom());
        largest.reserves = reserves;
        this.recalculateIncome();
    }

    private void recalculateIncome(){
        income = lands.size() - lands.stream().mapToInt(Hexagon::getCost).sum();
    }

    SlayMap getMap() {
        return map;
    }

    @SuppressWarnings("WeakerAccess")
    public int size(){
        return lands.size();
    }

    private List<Set<Hexagon>> getSeperateRegions(Hexagon removedPoint){
        List<Set<Hexagon>> regions = new ArrayList<>();
        for (Hexagon neighbor: getNeighbors(removedPoint)){
            if (regions.stream().anyMatch(r -> r.contains(neighbor))){
                continue;
            }
            Set<Hexagon> visited = new HashSet<>();
            GameMap.bfs(removedPoint, p -> {visited.add(p); return false;}, this::getNeighbors);
            regions.add(visited);
        }
        return regions;
    }

    public void updateCharge(int amount){
        income -= amount;
    }

    public Set<Hexagon> getNeighbors(Hexagon point){
        Set<Hexagon> neighbors = map.connections(point);
        neighbors.removeIf(p -> !contains(p));
        return neighbors;
    }

    public boolean contains(Hexagon point){
        return lands.contains(point);
    }
}
