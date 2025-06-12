package com.mycompany.turnbasedgame.Locations;

import com.mycompany.turnbasedgame.Game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GrandLine extends Location{

    {
        locationName = "Grand Line";
    }
    
    public static String mapOfGraph = """
                                      =================================== Grand Line MAP: ====================================
                                      
                                                     /- Baratie Restaurant --------------- Little Garden
                                                    /          |        \\                           \\     
                                                   /           |         \\     Jaya Island           \\  Dressrosa
                                      Logue Town ------- Gecko Island---  \\ ----/ /    \\              \\ /       \\
                                                  |\\                       \\     /      \\              \\         \\         
                                                  | \\                       \\   /        Alabasta-----/ \\        NEW WORLD
                                                  |  \\-Okyot Island -------- Kyuka                       \\       /
                                                  |                           /  \\__                    Whole Cake
                                                  |                          /       \\__                     /
                                             Marine Base -------------------/            Sabaody------------/
                                      
                                      =========================================================================================
                                      """;
    
    @Override
    public void miniGame() throws InterruptedException {
        
        Graph grandLineGraph = new Graph();
        
        System.out.printf("%nCurrent Objective : `Find The Best Path to Reach `New World`!!%n%n%s%n", mapOfGraph);
        
        String currentGrandLineLocation = "Logue Town";
        
        byte pathsRemaining = 4;
        
        while(true) {
            
            byte choice, i = 0;
            
            List<String> currentGrandLineLocationEdges = grandLineGraph.getEdge(currentGrandLineLocation);
            
            System.out.printf("%nPaths Remaining : %d%nCurrent Grand Line Location *Arhg* : %s%n", pathsRemaining, currentGrandLineLocation);
            for (String edge : currentGrandLineLocationEdges) System.out.printf("Type `%d` to go %s%n", i++, edge);
            System.out.print("\nChoose The Best Path :P : ");
            
            try {
                
                choice = s.nextByte(); s.nextLine();
                
                if(choice >= 0 && choice <= i) {currentGrandLineLocation = currentGrandLineLocationEdges.get(choice); pathsRemaining--;}

                if("New World".equals(currentGrandLineLocation)) break;
                else if (pathsRemaining <= 0) {
                    System.out.printf("%nThe Path is Too Long D:! %n%nCurrent Objective : `Find The Best Path to Reach `New World`!!%n%s%n", mapOfGraph);
                    currentGrandLineLocation = "Logue Town"; pathsRemaining = 4;
                }
            }
            catch(Exception e) {System.out.println("Invalid Choice");}
        }
    }

    @Override
    public void bossBattle() throws InterruptedException {
        
        String message = "\n*Boss*: ........-*Boss* : YOU DARE TO ENTER MY TURF >:(~~-*Boss* : NOW I'LL SHOW YOU THE QUICKEST PATH TO HELL >:)~~-*Boss* : ZEHAHAHAHAHAHA >:D`\n";
        for(var sentence : message.split("-")) {System.out.println(sentence); Thread.sleep(3500L);}

        System.out.println("----------------!! BOSS BATTLE !!-------------------");

        Game.encounter("Black Beard", 80, "Grand Line Boss", 40, 20, 100);
    }

    @Override
    public void printMessage() throws InterruptedException{
        
        String message = "\n\n........-You Seem to Have Reached a new Line :O~-The Grand Line!! :OO~-It Seems that Our Boat is almost Broken :(-We must Find The Shortest Path :P\n";
        for(var sentence : message.split("-")) {System.out.println(sentence); Thread.sleep(5500L);}
    }
}


class Graph {
    
    HashMap<String, ArrayList<String>> entries = new HashMap<>();

    public Graph () {
        
        // Adding the Vertices
        List<String> vertices = List.of("Logue Town", "Baratie Restaurant", "Gecko Island", "Oykot Kingdom", 
                                        "Marine Base", "Little Garden", "Jaya Island","Kyuka","Alabasta",
                                        "Sabaody","Dressrosa","Whole Cake","New World");
        
        vertices.forEach(e -> addVertex(e));
        
        // Adding the Edges of the Vertices
        addEdge("Logue Town", "Baratie Restaurant", "Gecko Island", "Oykot Kingdom", "Marine Base");
        addEdge("Baratie Restaurant", "Gecko Island", "Kyuka", "Little Garden");
        addEdge("Gecko Island", "Jaya Island");
        addEdge("Oykot Kingdom", "Kyuka");
        addEdge("Marine Base", "Kyuka");
        addEdge("Little Garden", "Whole Cake");
        addEdge("Jaya Island", "Kyuka", "Alabasta");
        addEdge("Kyuka", "Sabaody");
        addEdge("Alabasta", "Dressrosa");
        addEdge("Sabaody", "Whole Cake");
        addEdge("Dressrosa", "New World");
        addEdge("Whole Cake", "New World");
    }
    
    public final boolean addVertex (String grandlineLocation) {
        if(entries.get(grandlineLocation) == null) {
            entries.put(grandlineLocation, new ArrayList<>()); return true;
        }
        return false;
    }
    
    public final boolean addEdge (String grandLineLocation1, String... grandLineLocations) {
        
        for (String location : grandLineLocations) {
            if(entries.containsKey(grandLineLocation1) && entries.containsKey(location)){
                entries.get(grandLineLocation1).add(location);
                entries.get(location).add(grandLineLocation1);
            }
            else return false;
        }
        return true;
    }
    
    public final List<String> getEdge(String grandLineLocation) {
        
        List edges = entries.get(grandLineLocation);
        
        if(edges != null) return edges;
        return null;
    }
}