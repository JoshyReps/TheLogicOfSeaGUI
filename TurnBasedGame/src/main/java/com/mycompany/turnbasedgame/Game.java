package com.mycompany.turnbasedgame;

import com.mycompany.turnbasedgame.Locations.Location;
import static com.mycompany.turnbasedgame.TurnBasedGame.s;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Game {
    
    public static Character player;
    
    public void startGame(Character player1) {
        
        Game.player = player1;
        
        boolean heart = true;
        
        while(heart) {
            
            // --------------------------- Open Menu -------------------------------
            System.out.println("""
                           -------------- Hello Gamer: "%s"! -------------
                           
                           What Would you like to Do?
                           1. -> Travel
                           2. -> Check Map
                           3. -> Fight Random
                           4. -> Exit
                           """.formatted(player1.playerName));
        
            System.out.print("Enter Input: ");
            // ---------------------------------------------------------------------
            
            try {
                
                int actionChoice = s.nextInt(); s.nextLine();
                
                if(actionChoice >= 1 && actionChoice <= 3) {
                    switch(actionChoice) {
                        case 1 -> travel();
                        case 2 -> System.out.println("""
                                    -------------- %s's Travel Map! --------------

                                    Area Coordinates :

                                    [0, 0] -> Spawn / Foosha Village
                                    [3, 12] -> Sky Island
                                    [-3, -12] -> Fish Island
                                    [15, 15] -> Grand Line
                                    [25, 25] -> The Land Of Wano
                                    Back to Menu
                                   """);
                        case 3 -> encounter("Random Bot", 100, " (BOT)", 10, 1, 50);
                        case 4 -> heart = false;
                    }
                }
                else System.out.println("Invalid Input");
                
            } 
            catch (InterruptedException e) {System.out.println("Thread Interrupted"); s.nextLine();}
            catch (InputMismatchException e) {System.out.println("That is an Invalid Input"); s.nextLine();}
        }
    }
    
    private void travel () throws InterruptedException {
        
        while(true) {
            
            String playerPostionString = Arrays.toString(player.playerPosition);
            Location currentPlayerLocation = Location.getLocation(playerPostionString);
            String locationName = Location.getLocationName(playerPostionString);
            
            if(Location.getLocation(playerPostionString) != null) {
                currentPlayerLocation.printMessage();
                currentPlayerLocation.miniGame();
                currentPlayerLocation.bossBattle();
            }
            
            System.out.println("""
                               %n--------------- Player Movement Control -------------
                               
                               Current Location : %s
                               
                               %s's Position : x - %d  y - %d
                               
                               "w" -> North
                               "a" -> West
                               "s" -> South
                               "d" -> East
                               "x" -> Back to Menu
                               -----------------------------------------------------%n
                               """.formatted(locationName, player.playerName, player.playerPosition[0], player.playerPosition[1]));
            
            System.out.print("Enter Movement: ");
            
            String inputAction = s.nextLine().trim().toLowerCase();
            
            if(inputAction.isEmpty()) System.out.println("Bruh Moment");
            else {
                
                if(inputAction.charAt(0) == 'x') break;

                switch(inputAction.charAt(0)) {
                    case 'w' -> player.playerPosition[1]++;
                    case 'a' -> player.playerPosition[0]--;
                    case 's' -> player.playerPosition[1]--;
                    case 'd' -> player.playerPosition[0]++;
                }
            }
        }
    }
    
    public static void encounter(String botName, int botHP, String additionalInfo, int maxDMG, int minDMG, int playerSpeed) throws InterruptedException {
        
        
        player.playerHP = 100;
        
        
        Monster bot = new Monster(botHP, "%s (%S)".formatted(botName, additionalInfo), maxDMG, minDMG, playerSpeed);
        
        while(true) {
            
            if(player.playerSpeed > bot.playerSpeed) {
                System.out.println("You are Faster!");
                player.actionInputSelection(bot);
                if(bot.isDefeated()) {System.out.println("You Have Won!"); break;}
                bot.actionInputSelection(player);
            }
            else {
                System.out.println("The Bot is Faster!");
                bot.actionInputSelection(player);
                if(player.isDefeated()) {System.out.println("You Have Lost"); break;}
                player.actionInputSelection(bot);
            }
        }
    }
}
