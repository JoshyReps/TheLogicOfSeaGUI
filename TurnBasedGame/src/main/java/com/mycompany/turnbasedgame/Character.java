package com.mycompany.turnbasedgame;

import java.util.Random;
import java.util.Stack;
import java.util.function.Consumer;

public abstract class Character {
    public static Random random = new Random();

    // Position in the world
    public int[] playerPosition = new int[]{0, 0};

    // Stacks for HP history and damage infliction
    Stack<Integer> playerHPStack = new Stack<>();
    Stack<Integer> damageInflicted = new Stack<>();
    Stack<String> actionHistoryStack = new Stack<>();

    protected String playerName;
    protected int playerHP;
    protected int playerMaxDMG;
    protected int playerMinDMG;
    protected int playerSpeed;
    public int stunned;
    public int burned;

    public Character(int playerHP, String playerName, int playerMaxDMG, int playerMinDMG, int playerSpeed) {
        this.playerName = playerName;
        this.playerHP = playerHP;
        this.playerMaxDMG = playerMaxDMG;
        this.playerMinDMG = playerMinDMG;
        this.playerSpeed = playerSpeed;
        playerHPStack.push(playerHP);
    }

    public void attack(Character enemy, Consumer<String> log) {
        int dmg = random.nextInt(playerMaxDMG) + playerMinDMG;
        enemy.playerHP = Math.max(0, enemy.playerHP - dmg);
        damageInflicted.push(dmg);
        playerHPStack.push(this.playerHP);
        log.accept(String.format("%s dealt %d damage. %s now has %d HP.",
                playerName, dmg, enemy.playerName, enemy.playerHP));
    }

    public void stun(Character enemy, Consumer<String> log) {
        if (random.nextInt(4) != 0) {
            log.accept(playerName + " tried to stun but failed.");
            return;
        }
        int stunTurns = random.nextInt(3) + 1;
        enemy.stunned = stunTurns;
        log.accept(String.format("%s stunned %s for %d turn(s).",
                playerName, enemy.playerName, stunTurns));
    }

    public void applyBurn(Consumer<String> log) {
        if (burned > 0) {
            playerHP = Math.max(0, playerHP - 3);
            log.accept(String.format("%s suffers 3 burn damage. %d turns left.",
                    playerName, burned));
            burned--;
        }
    }

    public boolean isDefeated() {
        return playerHP <= 0;
    }

    public void encounterInputAction(Character self, Character enemy, String action, Consumer<String> log) {
        actionHistoryStack.push(action);
        if (self.stunned > 0) {
            log.accept(self.playerName + " is stunned! " + self.stunned + " turn(s) left.");
            self.stunned--;
            return;
        }
        applyBurn(log);
        switch (action) {
            case "attack" -> attack(enemy, log);
            case "stun"   -> stun(enemy, log);
            case "skip"   -> log.accept(self.playerName + " skipped the turn.");
            default        -> log.accept("Invalid action: " + action);
        }
    }

    public abstract void actionInputSelection(Character enemy, Consumer<String> log);
}