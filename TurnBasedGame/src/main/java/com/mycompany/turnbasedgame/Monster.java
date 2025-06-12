// Monster.java
package com.mycompany.turnbasedgame;

import java.util.Random;
import java.util.function.Consumer;

public class Monster extends Character {
    private final Random random = new Random();

    public Monster(int playerHP, String playerName, int playerMaxDMG, int playerMinDMG, int playerSpeed) {
        super(playerHP, playerName, playerMaxDMG, playerMinDMG, playerSpeed);
    }

    private void healingPassive(Consumer<String> log) {
        if (playerHPStack.size() > 1 && random.nextInt(4) == 3) {
            playerHPStack.pop();
            playerHP = playerHPStack.peek();
            log.accept(playerName + " healed passive to " + playerHP + " HP.");
        }
    }

    private void parryPassive(Character enemy, Consumer<String> log) {
        if (random.nextInt(16) == 1) {
            double boost = new double[]{0.05,0.1,0.15,0.20,0.25}[random.nextInt(5)];
            playerHP += (int)(playerHP * boost);
            playerMinDMG += (int)(playerMinDMG * boost);
            playerMaxDMG += (int)(playerMaxDMG * boost);
            log.accept("*Parry* boosted HP and DMG by " + (int)(boost*100) + "%.");
        }
    }

    private void unoReverse(Consumer<String> log) {
        if (!damageInflicted.isEmpty() && random.nextInt(10) == 0) {
            int dmg = damageInflicted.pop();
            playerHP += dmg;
            log.accept(playerName + " used Uno Reverse: healed " + dmg + " and returned damage.");
        }
    }

    @Override
    public void actionInputSelection(Character enemy, Consumer<String> log) {
        log.accept("\n-- " + playerName + "'s Turn --");
        healingPassive(log);
        unoReverse(log);
        String[] moves = {"attack","stun","skip"};
        String choice = moves[random.nextInt(moves.length)];
        encounterInputAction(this, enemy, choice, log);
        parryPassive(enemy, log);
    }
}