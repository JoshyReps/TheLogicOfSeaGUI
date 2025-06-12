
package com.mycompany.turnbasedgame.Locations;

import com.mycompany.turnbasedgame.Game;
import java.util.Stack;

public class FishIsland extends Location  {

    {
        locationName = "Fish Island";
    }
    
    @Override
    public void miniGame() throws InterruptedException {
        
        BinaryTree oceanLocationTree = new BinaryTree();
        Stack<Node> stackDepthNode = new Stack();
        
        stackDepthNode.push(oceanLocationTree.root);
        
        System.out.printf("%nCurrent Objective : `Dive into the Washed Abyss`, Also, Try Not to Hit a Dead End :)..%n%n");
        
        while(true) {
            
            Node currentLocationNode = stackDepthNode.peek();
            
            if("Washed Abyss".equals(currentLocationNode.depthName)) return;
            
            System.out.printf("Current Underwater Location : %s%n", stackDepthNode.peek().depthName);
            
            if(currentLocationNode.right == null || currentLocationNode.left == null) {
                System.out.println("\nYou Have Reached a Dead End D:!, You Have to Go Back~\n>> Type `undo` go Back\n");
            }
            else {
                System.out.println("""
                                   
                               Where Do You Want to Go Next :
                               >> type `r` to Go Right -> %s
                               >> type `l` to Go Left -> %s
                               >> type `undo` to Go Back
                                   
                               """.formatted(currentLocationNode.right.depthName, 
                                             currentLocationNode.left.depthName));
            }
            
            System.out.print("Where Do You Wanna Dive :P? ");
            String choice = s.nextLine().toLowerCase().trim();
            
            switch(choice) {
                case "r" -> {if(currentLocationNode.right == null) System.out.println("Not Possible"); 
                             else stackDepthNode.push(currentLocationNode.right);}
                case "l" -> {if(currentLocationNode.left == null) System.out.println("Not Possible"); 
                             else stackDepthNode.push(currentLocationNode.left);}
                case "undo" -> {if(stackDepthNode.size() == 1) System.out.println("Not Possible"); 
                             else System.out.printf("Went Back to %s%n", stackDepthNode.pop().depthName);}
                default -> {System.out.println("That is Not a Valid Choice!");}
            }
        }
    }

    @Override
    public void bossBattle() throws InterruptedException {
        
        String message = "\n*Boss*: ........-*Boss* : So You Have Managed to Find Me <:)~~-*Boss* : You're Pretty Good >:D~~-*Boss* : Unfortunately, You Cannot Undo This Mistake!! JAJAJAJA!!!`\n";
        for(var sentence : message.split("-")) {System.out.println(sentence); Thread.sleep(3500L);}
        
        System.out.println("----------------!! BOSS BATTLE !!-------------------");
        
        Game.encounter("Aqua-Man", 150, "Fish Island Boss", 20, 9, 50);
    }

    @Override
    public void printMessage() throws InterruptedException{
        
        String message = "\n\n........-We have Arrived at Our New Desination :)~-Although, Nothing seems to be Here D:~-Let's Try Checking Underwater >:P\n";
        for(var sentence : message.split("-")) {System.out.println(sentence); Thread.sleep(5500L);}
    }
}


class Node {
        
    String depthName;
    Node left;
    Node right;

    public Node (String depthName) {
        this.depthName = depthName;
    }
}

class BinaryTree {
    
    Node root;
    
    public BinaryTree () {
        
        insert("Surface");
        insert("Luke Deep");
        insert("Warn Deep");
        insert("Coral Reef");
        insert("Ravine");
        insert("Cavern");
        insert("Deep Water");
        insert("Viraaj Sunken");
        insert("Xisha Trough");
        insert("Water Cave");
        insert("Zenleviah Submarine Canyon");
        insert("Waved Laguna");
        insert("Washed Abyss");
    }
    
    // -------------- Insert Method (recursive) ------------
    public final void insert (String depthName) {
        if(root == null) root = new Node(depthName);
        else insertRec(depthName, root);
    }
    
    public Node insertRec (String depthName, Node node) {
        
        if(node == null) return new Node(depthName);
        
        if(depthName.compareTo(node.depthName) > 0) {
            node.left = insertRec(depthName, node.left);
        }
        else if (depthName.compareTo(node.depthName) < 0) {
            node.right = insertRec(depthName, node.right);
        }
        
        return node;
    }
    // -----------------------------------------------
}