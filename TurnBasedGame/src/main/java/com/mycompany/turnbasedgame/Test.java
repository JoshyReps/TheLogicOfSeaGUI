
package com.mycompany.turnbasedgame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Test {
    
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
    
    public static void main(String... args){
        System.out.println(mapOfGraph);
        new Graph();
    }
}


class Graph {
    
    HashMap<String, LinkedList<String>> entries = new HashMap<>();

    public Graph () {
        
        // Adding the Vertices
        List<String> vertices = List.of("Logue Town", "Baratie Restaurant", "Gecko Island", "Oykot Kingdom", 
                                        "Marine Base", "Little Garden", "Jaya Island","Kyuka","Alabasta",
                                        "Sabaody","Dressrosa","Whole Cake","New World");
        
        vertices.forEach(e -> addVertex(e));
        
        // Adding the Edges of the Vertices
        addEdge("Logue Town", "Baratie", "Gecko Island", "Oykot Kingdon", "Marine Base");
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
        
        System.out.println(entries);
    }
    
    public final boolean addVertex (String grandlineLocation) {
        if(entries.get(grandlineLocation) == null) {
            entries.put(grandlineLocation, new LinkedList<>()); return true;
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
}