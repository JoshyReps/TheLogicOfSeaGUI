package com.mycompany.turnbasedgame;

import java.util.Random;
import java.util.Stack;

public class Monster extends Character {
    
    public Monster(int playerHP, String playerName, int playerMaxDMG, int playerMinDMG, int playerSpeed) {
        super(playerHP, playerName, playerMaxDMG, playerMinDMG, playerSpeed);
    }

    private void healingPassive(Character character, Character enemy) {

        if(character.playerHPStack.size() <= 1 || new Random().nextInt(4) + 1 != 4) return;
        character.playerHPStack.pop();
        character.playerHP = character.playerHPStack.peek();
        System.out.printf("%s's *Passive Healing Ability* has healed itself back to %s%n", character.playerName, character.playerHP);
    }

    
    // ----------------------------------- Parry by Mark Vincent Palencia ----------------------------------------
    // ===========================================================================================================
    private void parryTechnique (Character player){
      int chance = random.nextInt(16);
      if(chance != 1) {
          return;
      }
      
      int chance2 = random.nextInt(5);
      double array [] = {0.05,0.1,0.15,0.20,0.25};
      double boost = array[chance2];
     
      System.out.println("*Parry Technique* Player Has Parried the Attack " + (boost * 100) + "% to HP and DMG");
      
      player.playerHP = (int)(player.playerHP + (player.playerHP * boost));
      player.playerMinDMG = (int)(player.playerMinDMG + (player.playerMinDMG * boost));
      player.playerMaxDMG = (int)(player.playerMaxDMG + (player.playerMaxDMG * boost));         
    }
    // ===========================================================================================================
        
    
     private void unoReverseTechnique (Character character, Character enemy) {

        Stack<Integer> damageInflictedStack = enemy.damageInflicted;

        if(damageInflictedStack.isEmpty()) damageInflictedStack.add(character.playerDMG);

        if (new Random().nextInt(10) == 0) {
            if (!damageInflictedStack.isEmpty()) {
                System.out.printf("%s has used *Uno Reversed Technique*. %s healed, %s damage returned %n", 
                        character.playerName,
                        damageInflictedStack.peek(),
                        damageInflictedStack.peek());
                        character.playerHPStack.push(character.playerHP += damageInflictedStack.peek());
                        enemy.playerDMG -= damageInflictedStack.pop();
            } 
            else System.out.println("UNO reverse card failed.");
        }
    }
    
    @Override
    void actionInputSelection(Character enemy) {
        
//        System.out.printf("%n%s is Analyzing their Next Brilliant Move!!%n".formatted(this.playerName)); Thread.sleep(7000L);
                
                String randomBotChoice = switch (random.nextInt(3) + 1) {case 1 -> "attack"; case 2 -> "stun"; case 3 -> "skip"; default -> "ran";}; 
                System.out.printf("%n----- %s at Play! (Random Choice) -----%n".formatted(this.playerName));
                healingPassive(this, enemy);
                unoReverseTechnique(this, enemy);
                encounterInputAction(this, enemy, randomBotChoice);
                if(randomBotChoice.equals(randomBotChoice)) parryTechnique(enemy);
                System.out.println("------------------------------------------"); //Thread.sleep(5000L);
    }
}
