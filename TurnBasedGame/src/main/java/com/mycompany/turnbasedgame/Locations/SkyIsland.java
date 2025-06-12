package com.mycompany.turnbasedgame.Locations;

import com.mycompany.turnbasedgame.Game;

public class SkyIsland extends Location {

    {
        locationName = "Sky Island";
    }
    
    @Override
    public void miniGame() throws InterruptedException {
        
        System.out.printf("%nCurrent Objective : `Reach The Top of the Sky Island`, Also, Try Not to Fall Down :)..%n");
        
        boolean[][] cloudsChoices2D ={
            {true,  false}, 
            {true,  false, false},
            {false, false, true, false},
            {false, false, false, true,  false},
            {false, true,  false, false, false, false}
        };
        
        int currentLevel = 0;
        
        while(true) {
            
            final boolean[] CURRENT_CLOUD_LEVEL = cloudsChoices2D[currentLevel];
            final byte CURRENT_LEVEL_ARRAY_LENGTH = (byte) cloudsChoices2D[currentLevel].length;
            
            System.out.printf("%nCurrent Level : %d%n",currentLevel + 1);
            System.out.println(cloudGenerator(CURRENT_LEVEL_ARRAY_LENGTH));
            System.out.print("Jump on Which Cloud :P ? ");
            
            byte choice = 0;
            
            try {
                choice = s.nextByte(); s.nextLine();
                if(choice < 0 && choice >= CURRENT_LEVEL_ARRAY_LENGTH) continue;
            }
            catch(Exception e) {System.out.println("Invalid Choice"); s.nextLine(); continue;}
            
            if(CURRENT_CLOUD_LEVEL[choice]) {
                System.out.println("Congratulations! You Found the Right Cloud :P");
                currentLevel++;
                if(currentLevel == 5) return;
            }
            else {
                System.out.println("Womp Womp! That Cloud was too Soft D:");
                if(currentLevel != 0) currentLevel--;
            }
        }
    }
    
    private static String cloudGenerator(int num) {
        
        StringBuilder cloudLine1 = new StringBuilder();
        StringBuilder cloudLine2 = new StringBuilder();
        StringBuilder cloudLline3 = new StringBuilder();

        for (int i = 0; i < num; i++) {
            cloudLine1.append(String.format("    _____       "));
            cloudLine2.append(String.format(" __(  %d  )_     ", i));
            cloudLline3.append(String.format("(___________)   "));
        }

        return "%s%n%s%n%s%n".formatted(cloudLine1.toString(), cloudLine2.toString(), cloudLline3.toString());
    }

    @Override
    public void bossBattle() throws InterruptedException{
        
        String message = "\n*Boss*: ........,*Boss* : That was Certainly Impressive~~,*Boss* : You Somehow Managed To Get On Top~~,*Boss* : Now You Must Perish!!! BAHAHAHAHA!!!`\n";
        for(var sentence : message.split(",")) {System.out.println(sentence); Thread.sleep(3500L);}
        
        System.out.println("----------------!! BOSS BATTLE !!-------------------");

        Game.encounter("Enel Zues", 125, "Sky Island Boss", 15, 5, 100);
    }

    @Override
    public void printMessage() throws InterruptedException{
        
        String message = "\n\n........,It would Seem That~,You have Ventured in a Different Location~,The Island Seems to be on The Sky~,We Need to Get Up There Somehow! :P\n";
        for(var sentence : message.split(",")) {System.out.println(sentence); Thread.sleep(5000L);}
    }
}
