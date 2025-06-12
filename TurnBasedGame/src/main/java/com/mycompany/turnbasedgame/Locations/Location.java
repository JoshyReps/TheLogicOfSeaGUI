package com.mycompany.turnbasedgame.Locations;

import com.mycompany.turnbasedgame.Monster;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Location {
    
    public static Scanner s = new Scanner(System.in);
    
    // -------------------- Location Area Coordinates ----------------------
        public static final HashMap<String, Location> LOCATIONS_MAP = new HashMap<>();
    // ---------------------------------------------------------------------
     
    public String locationName;
    public Monster bossMonster;
        
    static {
        LOCATIONS_MAP.put("[3, 12]", new SkyIsland());
        LOCATIONS_MAP.put("[-3, -12]", new FishIsland());
        LOCATIONS_MAP.put("[15, 15]", new GrandLine());
        LOCATIONS_MAP.put("[25, 25]", new LandOfWano());
    }
    
    public static Location getLocation(String playerCoordinates) {
        if(LOCATIONS_MAP.get(playerCoordinates) == null) return null;
        else {
            return LOCATIONS_MAP.get(playerCoordinates);
        }
    }
    
    public static String getLocationName (String playerCoordinates) {
        if(LOCATIONS_MAP.get(playerCoordinates) == null) return "Wilderness";
        else if ("[0,0]".equals(playerCoordinates)) return "Spawn";
        else {
            return LOCATIONS_MAP.get(playerCoordinates).locationName;
        }
    }
    
    public abstract void printMessage() throws InterruptedException;
    public abstract void miniGame() throws InterruptedException;
    public abstract void bossBattle() throws InterruptedException;
}
