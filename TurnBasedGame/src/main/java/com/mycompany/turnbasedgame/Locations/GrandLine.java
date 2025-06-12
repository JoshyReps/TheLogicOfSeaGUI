package com.mycompany.turnbasedgame.Locations;

import com.mycompany.turnbasedgame.EncounterGUI;
import com.mycompany.turnbasedgame.Monster;
import com.mycompany.turnbasedgame.TurnBasedGame;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class GrandLine extends Location {

    private final JFrame frame;
    private final JTextArea outputArea;
    private final JPanel buttonPanel;
    private final Graph grandLineGraph;
    private String currentLocation = "Logue Town";
    private int pathsRemaining = 4;
    private final String goal = "New World";

    private static final String MAP_IMAGE_PATH = "C:\\Users\\MARIFER\\Documents\\NetBeansProjects\\TurnBasedGame\\src\\main\\java\\grandLineImages\\grandLineGraph.png";

    {
        frame = new JFrame("🌊 Grand Line Explorer");
        grandLineGraph = new Graph();
        outputArea = new JTextArea(8, 50);
        buttonPanel = new JPanel();
    }
    
    public void start () {
        
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(Color.WHITE);

        // === Map ===
        ImageIcon mapIcon = new ImageIcon(MAP_IMAGE_PATH);
        Image mapImage = mapIcon.getImage().getScaledInstance(700, 250, Image.SCALE_SMOOTH);
        JLabel mapLabel = new JLabel(new ImageIcon(mapImage));
        mapLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mapLabel.setBorder(new EmptyBorder(10, 10, 0, 10));
        frame.add(mapLabel, BorderLayout.NORTH);

        // === Output Text Area ===
        
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        outputArea.setForeground(Color.BLACK);
        outputArea.setBackground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(10, 10, 10, 10)));
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // === Button Panel ===
        
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        printIntro();
        showCutscene();
        updateGameState();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void printIntro() {
        appendMessage("🌟 Welcome to the Grand Line Adventure!");
        appendMessage("Your Objective: Find the best path to reach the `New World` within 4 moves.\n");
    }

    private void appendMessage(String msg) {
        outputArea.append(msg + "\n");
    }

    private void updateGameState() {
        buttonPanel.removeAll();
        appendMessage(String.format("🌍 Current Location: %s", currentLocation));
        appendMessage("🧭 Paths Remaining: " + pathsRemaining);

        List<String> options = grandLineGraph.getEdge(currentLocation);
        if (options == null) return;

        for (String destination : options) {
            JButton btn = new JButton("Go to: " + destination);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setBackground(Color.BLACK);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

            btn.addActionListener(e -> {
                currentLocation = destination;
                pathsRemaining--;

                if (goal.equals(currentLocation)) {
                    appendMessage("\n🎉 You've successfully reached the New World!");
                    frame.dispose();
                    showBossBattleCutscene();
                    return;
                } else if (pathsRemaining <= 0) {
                    appendMessage("\n❌ You took too long! Resetting journey...");
                    currentLocation = "Logue Town";
                    pathsRemaining = 4;
                }
                updateGameState();
            });

            buttonPanel.add(Box.createVerticalStrut(5));
            buttonPanel.add(btn);
        }

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    private void showBossBattleCutscene() {
        String[] bossLines = {
            "*Boss*: ........",
            "*Boss*: YOU DARE TO ENTER MY TURF >:(",
            "*Boss*: NOW I'LL SHOW YOU THE QUICKEST PATH TO HELL >:)",
            "*Boss*: ZEHAHAHAHAHAHA >:D"
        };

        JDialog bossDialog = new JDialog(frame, "⚔️ Boss Encounter", true);
        bossDialog.setLayout(new BorderLayout());

        JLabel bossText = new JLabel("", SwingConstants.CENTER);
        bossText.setFont(new Font("Serif", Font.BOLD, 20));
        bossText.setForeground(Color.BLACK);
        bossText.setBackground(Color.WHITE);
        bossText.setOpaque(true);
        bossText.setBorder(new EmptyBorder(30, 30, 30, 30));

        bossDialog.add(bossText, BorderLayout.CENTER);
        bossDialog.setSize(650, 180);
        bossDialog.setLocationRelativeTo(frame);

        final int[] i = {0};
        Timer timer = new Timer(3000, null);
        timer.addActionListener(e -> {
            if (i[0] < bossLines.length) {
                bossText.setText(bossLines[i[0]++]);
            } else {
                timer.stop();
                bossDialog.dispose();
            }
        });

        timer.setInitialDelay(0);
        timer.start();
        bossDialog.setVisible(true); 
        EncounterGUI.startEncounter(TurnBasedGame.hero, new Monster(80, "Black Beard (Grand Line Boss)", 30, 15, 100));
    }

    private void showCutscene() {
        String[] cutsceneLines = {
            "........",
            "You Seem to Have Reached a new Line :O~",
            "The Grand Line!! :OO~",
            "It Seems that Our Boat is almost Broken :(",
            "We must Find The Shortest Path :P"
        };

        JDialog dialog = new JDialog(frame, "Grand Line Cutscene", true);
        dialog.setLayout(new BorderLayout());

        JLabel textLabel = new JLabel("", SwingConstants.CENTER);
        textLabel.setFont(new Font("Serif", Font.BOLD, 20));
        textLabel.setForeground(Color.BLACK);
        textLabel.setBackground(Color.WHITE);
        textLabel.setOpaque(true);
        textLabel.setBorder(new EmptyBorder(30, 30, 30, 30));
        dialog.add(textLabel, BorderLayout.CENTER);
        dialog.setSize(600, 180);
        dialog.setLocationRelativeTo(frame);

        final int[] i = {0};
        Timer timer = new Timer(2800, null);
        timer.addActionListener(e -> {
            if (i[0] < cutsceneLines.length) {
                textLabel.setText(cutsceneLines[i[0]++].replace("~", ""));
            } else {
                timer.stop();
                dialog.dispose();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new GrandLine().start();
    }
}


class Graph {
    
    HashMap<String, ArrayList<String>> entries = new HashMap<>();

    public Graph() {
        List<String> vertices = List.of("Logue Town", "Baratie Restaurant", "Gecko Island", "Oykot Kingdom",
                "Marine Base", "Little Garden", "Jaya Island", "Kyuka", "Alabasta",
                "Sabaody", "Dressrosa", "Whole Cake", "New World");

        vertices.forEach(this::addVertex);

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

    public final boolean addVertex(String location) {
        if (!entries.containsKey(location)) {
            entries.put(location, new ArrayList<>());
            return true;
        }
        return false;
    }

    public final boolean addEdge(String from, String... to) {
        for (String dest : to) {
            if (entries.containsKey(from) && entries.containsKey(dest)) {
                entries.get(from).add(dest);
                entries.get(dest).add(from);
            } else return false;
        }
        return true;
    }

    public final List<String> getEdge(String location) {
        return entries.getOrDefault(location, new ArrayList<>());
    }
}
