// Hero.java
package com.mycompany.turnbasedgame;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.function.Consumer;

public class Hero extends Character {
    private final Queue<Integer> attackStack = new LinkedList<>();
    private final Random random = new Random();

    public Hero(int playerHP, String playerName, int playerMaxDMG, int playerMinDMG, int playerSpeed) {
        super(playerHP, playerName, playerMaxDMG, playerMinDMG, playerSpeed);
    }

    private void jinguMastery(Character self, Character enemy, Consumer<String> log) {
        attackStack.add(self.playerMaxDMG);
        if (attackStack.size() % 4 == 0) {
            if (random.nextBoolean()) {
                log.accept("*Jingu Mastery* double damage activated!");
                self.playerMaxDMG *= 2;
            } else {
                log.accept("*Jingu Mastery* burn applied for 3 turns!");
                enemy.burned = 3;
            }
        }
    }

    @Override
    public void actionInputSelection(Character enemy, Consumer<String> log) {
        // GUI driver calls performAction instead
    }

    public void performAction(String action, Character enemy, Consumer<String> log) {
        log.accept("\n-- " + playerName + "'s Turn --");
        if ("attack".equals(action)) jinguMastery(this, enemy, log);
        encounterInputAction(this, enemy, action, log);
    }
}