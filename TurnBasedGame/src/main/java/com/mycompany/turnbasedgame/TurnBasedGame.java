package com.mycompany.turnbasedgame;

import com.mycompany.turnbasedgame.Locations.FishIsland;
import java.util.Random;
import java.util.Scanner;

public class TurnBasedGame {

    public static Random random = new Random();
    public static Scanner s = new Scanner(System.in);
    public static Game game;
    
    // ------------------------------------  HP    Name   Max Min  Passive Ability
    public static Hero hero = new Hero(100, "Luffy", 25, 15, 12);
    // ----------------------------------------------------------------------------
    
    public static void main(String[] args) {
        
         game = new Game(hero);
    }
    // ============================================================================================================
    // ------------------------------------------------------------------------------------------------------------
}