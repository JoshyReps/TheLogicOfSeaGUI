package com.mycompany.turnbasedgame.Locations;

import com.mycompany.turnbasedgame.Game;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LandOfWano extends Location {

    {
        locationName = "Land Of Wano";
    }
    
    @Override
    public void miniGame() throws InterruptedException {
        
        System.out.printf("%nCurrent Objective : `Help Fix the Country by Finsihing all of the Quests`, Don't mess up huhuhu..%n");
        
        Queue<String> quest = new LinkedList(List.of("Quest 1: Head to `Kuri`",
                                                     "Quest 2: Destroy the Prison `Udon`",
                                                     "Quest 3: Kill the `Sea Beast`",
                                                     "Quest 4: Add a `Bridge` between `Ringo` and `Hakumai`",
                                                     "Quest 5: Add a `Wall of Mountain` before `Kuri` and after `Hakumai`"
                                                    ));
        
        DoublyLinkedList wanoIslands = new DoublyLinkedList(" Reverse Mountain ", "       Kuri       ", "       Udon       ", "       Kibi       ", 
                                                            "     Sea Beast    ", "  Flower Capital  ", "       Ringo      ", "      Hakumai     ");

        
        var currentIsland = wanoIslands.head;
        
        while(true) {
            
            System.out.printf("%n%s%n%s%n%s%n%n","-".repeat(50),quest.peek(),"-".repeat(50));
            wanoIslands.printIslands(currentIsland);
            
            System.out.println("""
                               --------------------------------------------
                               Move :
                                 Type `a` to Move Left
                                 Type `d` to Move Right
                               Insert :
                                 Type `w` to Insert Wall
                                 Type `b` to Insert Bridge
                               Delete :
                                 Type `k` to Destroy/Kill
                               Next Quest :
                                 Type `n` for Next Quest
                               Restart :
                                 Type `r` to Restart
                               --------------------------------------------
                               """);
            
            System.out.print("Enter Action :P : ");
            
            char action;
            
            try {action = s.nextLine().charAt(0);}
            catch(Exception e) {System.out.println("Invalid Choice"); continue;}
            
            switch(action) {
                case 'a' -> {if(currentIsland.prev != null) currentIsland = currentIsland.prev;}
                case 'd' -> {if(currentIsland.next != null) currentIsland = currentIsland.next;}
                case 'w' -> {wanoIslands.insert(currentIsland, " Wall of Mountain ");}
                case 'b' -> {wanoIslands.insert(currentIsland, "     Bridge       ");}
                case 'k' -> {currentIsland = currentIsland.prev; wanoIslands.remove(currentIsland.next);}
                case 'n' -> {
                    
                    quest.poll();
                    
                    if(quest.isEmpty()) {
                        
                         DoublyLinkedList expectedResult = new DoublyLinkedList(" Reverse Mountain ", " Wall of Mountain ", "       Kuri       ", "       Kibi       ", "  Flower Capital  ", 
                                                                                "       Ringo      ", "     Bridge       ", "      Hakumai     ", " Wall of Mountain ");
                        
                        if(wanoIslands.equals(expectedResult)) return;
                        else {expectedResult.printIslands(currentIsland); System.out.println("\nThe Order isn't Quiet Right... Try Again! :("); miniGame();}
                    }
                }
                case 'r' -> miniGame();
            }
        }
    }

    @Override
    public void bossBattle() throws InterruptedException {
        
        String message = "\n*Boss* : YOU FOOL!!!~-*Boss* : HOW DARE YOU RUIN MY PERFECT TERRITORY!~-*Player* : Is that What I did?-*Boss* : DO NOT THINK YOU'LL GET AWAY!-*Player* : .....-*Player* : I Suppose I'll Just Ruin you As well >:)`\n";
        for(var sentence : message.split("-")) {System.out.println(sentence); Thread.sleep(4500L);}
        
        System.out.println("----------------!! BOSS BATTLE !!-------------------");
        
        Game.encounter("Yonko Kaido", 200, "The Land Of Wano Boss", 40, 15, 200);
    }

    @Override
    public void printMessage() throws InterruptedException{
        
        String message = "\n\n........-Atlast, We have Made it >:)~-The Land of Samurias!!! :D-There are so Many Locations Here :O.....-Although, This Country Seems to Be in a lot of Problems :(-Let's Help Them and Defeat the Final Boss:)\n";
        for(var sentence : message.split("-")) {System.out.println(sentence); Thread.sleep(5500L);}
    }
}




class DoublyLinkedList {
    
    Node head;
    Node tail;
    
    public DoublyLinkedList (String... islands) {
        for (String island : islands) append(island);
    }
    
    class Node {
        
        String island;
        Node next;
        Node prev;

        public Node (String island) {
            this.island = island;
        }
    }
    
    public final void append(String island) {
        
        Node newIsland = new Node(island);
        
       if(head == null) {
           head = newIsland;
           tail = newIsland;
       }
       else {
           tail.next = newIsland;
           newIsland.prev = tail;
           tail = newIsland;
       }
    }

    
    public final void insert(Node currentIsland, String island) {
        
        Node newIsland = new Node(island);
        
       if (currentIsland == tail) append(island);
       else {
           
           Node afterCurrent = currentIsland.next;
           
           newIsland.next = afterCurrent;
           newIsland.prev = currentIsland;
           
           currentIsland.next = newIsland;
           afterCurrent.prev = newIsland;
       }
    }
    
    public final void remove(Node currentIsland) {

        if(currentIsland != head && currentIsland != tail) {
            currentIsland.next.prev = currentIsland.prev;
            currentIsland.prev.next = currentIsland.next;
            currentIsland.next = null;
            currentIsland.prev = null;
        }
        else System.out.println("\nCannot Remove the Head Or Tail!\n");
    }
    
    public final void printIslands (Node currentIsland) {
        
        Node temp = head;

        while (temp != null) {
            if (temp == currentIsland) System.out.print("         V        ");
            else System.out.print("                  "); 
            temp = temp.next;
        }
        
        System.out.println();
        temp = head;
        
        while (temp != null) {
            System.out.print(temp.island);
            temp = temp.next;
        }
        System.out.println();
    }
    
    public final boolean equals (DoublyLinkedList dll) {
        
        Node temp1 = this.head;
        Node temp2 = dll.head;
        
        while(temp1 != null) {
            if(temp1.island.equals(temp2.island)) {
                temp1 = temp1.next;
                temp2 = temp2.next;
            }
            else return false;
        }
        return true;
    } 
}
