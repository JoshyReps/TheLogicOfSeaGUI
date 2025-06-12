package com.mycompany.turnbasedgame;

// ================================================================================================================

import java.util.Random;
import java.util.Stack;

// ---------------------------- Custom Class for Character (Players or Bot) ---------------------------------------
public abstract class Character {
        
    // Random Field
    public static Random random = new Random();

    // Stack Fields
    Stack<Integer> playerHPStack = new Stack<>();
    Stack<Integer> damageInflicted = new Stack<>();
    Stack<String> actionHistoryStack = new Stack<>();

    int[] playerPosition;
    
    // Stats / Fileds For Every Character
    protected String playerName;
    protected int playerHP;
    protected int playerDMG;
    protected int playerMaxDMG;
    protected int playerMinDMG;
    protected int playerSpeed;
    
    // Effects Field (From Abilities)
    public int stunned;
    public int burned;


    public Character(int playerHP, String playerName, int playerMaxDMG, int playerMinDMG, int playerSpeed) {
        
        
        this.playerName = playerName;
        this.playerHP = playerHP;
        this.playerMaxDMG = playerMaxDMG;
        this.playerMinDMG = playerMinDMG;
        this.playerSpeed = playerSpeed;
        
        this.playerPosition = new int[2];
        
        playerHPStack.push(playerHP);
    }

    
    public void attack(Character enemy) {

        playerDMG = random.nextInt(playerMaxDMG) + playerMinDMG;

        System.out.print("""
                        %s has dealt %d Damage
                        %s has now %d HP
                         """.formatted( playerName, 
                                        playerDMG,
                                        enemy.playerName, 
                                        enemy.damageAttack(playerDMG)));
        damageInflicted.push(playerDMG);
        enemy.playerHPStack.push(enemy.playerHP);
    }

    
    public void stun(Character enemy) {
        
        if(new Random().nextInt(4) != 0) {
            System.out.println("Tried to Stun But Failed"); return;
        }
        
        int stunAmount = random.nextInt(3) + 1;
        System.out.printf("%s have Stun %s by %d Turn!%n".formatted(playerName, 
                                                          enemy.playerName, 
                                                          enemy.stunned = stunAmount));
    }
    
    
    public int damageAttack(int damageDealth) {
        if(playerHP - damageDealth <= 0) playerHP = 0;
        else playerHP -= damageDealth;
        return playerHP;
    }
    
    
    public boolean isDefeated() {
        return playerHP <= 0;
    }
    
    
    public void encounterInputAction (Character character, Character enemy, String stringInput) {
        
        character.actionHistoryStack.push(stringInput);
        
        if(character.stunned != 0) {
            System.out.printf("You are Stunned By %d turns left %n", character.stunned--);
            return;
        }
        
        if(character.burned != 0) {
            System.out.printf("%s Has been burned to %d HP   %d turns left%n", character.playerName, 
                                                                         character.playerHP -= 3,
                                                                         character.burned--);
        }
        
        switch(stringInput) {
            case "attack" -> {
                character.attack(enemy);
            }
            case "stun" -> {
                character.stun(enemy);
            }
            case "skip" -> {
                System.out.println("Skipped Turn");
            }
            default -> {
                System.out.println("That is not a valid Action!!");
            }
        }
    }
    
    abstract void actionInputSelection(Character enemy);
}
// ----------------------------------------------------------------------------------------------------------------------
// ======================================================================================================================