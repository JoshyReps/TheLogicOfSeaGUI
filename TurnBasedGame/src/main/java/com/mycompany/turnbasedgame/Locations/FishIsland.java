package com.mycompany.turnbasedgame.Locations;

import com.mycompany.turnbasedgame.EncounterGUI;
import com.mycompany.turnbasedgame.Monster;
import com.mycompany.turnbasedgame.TurnBasedGame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Stack;

public class FishIsland extends Location {

    private final JFrame frame;
    private JLabel currentLocationLabel;
    private JTextArea messageArea;
    private JButton leftButton, rightButton, undoButton;

    private final BinaryTree oceanTree;
    private final Stack<Node> locationStack;

    {
        oceanTree = new BinaryTree();
        locationStack = new Stack<>();
        frame = new JFrame("Fish Island - Underwater Exploration");
    }
    
    public void start () {
        
        locationStack.push(oceanTree.root);
        showIntroCutsceneThenStartGame();
        setupGUI();
        updateDisplay();
    }

    private void setupGUI() {
        frame.setVisible(true);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        // Current Location Label
        currentLocationLabel = new JLabel("Current Location: ", SwingConstants.CENTER);
        currentLocationLabel.setFont(new Font("Arial", Font.BOLD, 22));
        currentLocationLabel.setForeground(Color.BLACK);

        // Objective Label
        JLabel objectiveLabel = new JLabel("🎯 Objective: Dive into the 'Washed Abyss' and avoid dead ends!", SwingConstants.CENTER);
        objectiveLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        objectiveLabel.setForeground(Color.DARK_GRAY);

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(currentLocationLabel);
        topPanel.add(objectiveLabel);

        frame.add(topPanel, BorderLayout.NORTH);

        // Message Area
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        messageArea.setBackground(Color.WHITE);
        messageArea.setForeground(Color.BLACK);
        messageArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.add(new JScrollPane(messageArea), BorderLayout.CENTER);

        // Navigation Panel with Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        leftButton = new JButton("⬅️ Go Left");
        rightButton = new JButton("➡️ Go Right");
        undoButton = new JButton("↩️ Undo");

        customizeButton(leftButton);
        customizeButton(rightButton);
        customizeButton(undoButton);

        buttonPanel.add(leftButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(rightButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        leftButton.addActionListener(e -> move("l"));
        rightButton.addActionListener(e -> move("r"));
        undoButton.addActionListener(e -> move("undo"));
    }

    private void customizeButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    private void move(String direction) {
        Node current = locationStack.peek();

        switch (direction) {
            case "r" -> {
                if (current.right == null) {
                    appendMessage("❌ Cannot go right. Dead end.");
                } else {
                    locationStack.push(current.right);
                }
            }
            case "l" -> {
                if (current.left == null) {
                    appendMessage("❌ Cannot go left. Dead end.");
                } else {
                    locationStack.push(current.left);
                }
            }
            case "undo" -> {
                if (locationStack.size() == 1) {
                    appendMessage("❌ Cannot undo, already at the surface.");
                } else {
                    Node popped = locationStack.pop();
                    appendMessage("↩️ Went back to " + locationStack.peek().depthName + " from " + popped.depthName);
                }
            }
        }

        updateDisplay();
    }

    private void updateDisplay() {
        Node current = locationStack.peek();
        currentLocationLabel.setText("Current Location: " + current.depthName);

        if ("Washed Abyss".equals(current.depthName)) {
            showBossCutscene();
            JOptionPane.showMessageDialog(frame, "🎉 You reached the Washed Abyss! Quest Complete!");
            frame.dispose();
            return;
        }

        if (current.left == null || current.right == null) {
            appendMessage("⚠️ You are at a dead end! Use Undo to go back.");
        } else {
            appendMessage("➡️ You can go:\n- Left: " + current.left.depthName + "\n- Right: " + current.right.depthName);
        }
    }

    private void appendMessage(String message) {
        messageArea.append(message + "\n\n");
    }

    private void showBossCutscene() {

        String[] bossLines = {
            "*Boss*: ........",
            "*Boss*: So You Have Managed to Find Me <:)~~",
            "*Boss*: You're Pretty Good >:D~~",
            "*Boss*: Unfortunately, You Cannot Undo This Mistake!! JAJAJAJA!!!`"
        };

        JDialog dialog = new JDialog(frame, "💀 Boss Encounter 💀", true);
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
        Timer timer = new Timer(3500, null);
        timer.addActionListener(e -> {
            if (i[0] < bossLines.length) {
                textLabel.setText(bossLines[i[0]++].replace("~", ""));
            } else {
                timer.stop();
                dialog.dispose();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
        dialog.setVisible(true);
        EncounterGUI.startEncounter(TurnBasedGame.hero, new Monster(100, "Aqua-Man (Fish Island Boss)", 20, 9, 50));
    }

    public static void showIntroCutsceneThenStartGame() {
        String[] cutsceneLines = {
            "........",
            "We have Arrived at Our New Destination :)~",
            "Although, Nothing seems to be Here D:~",
            "Let's Try Checking Underwater >:P"
        };

        JDialog dialog = new JDialog((Frame) null, "🌊 Fish Island Cutscene 🌊", true);
        dialog.setLayout(new BorderLayout());

        JLabel textLabel = new JLabel("", SwingConstants.CENTER);
        textLabel.setFont(new Font("Serif", Font.BOLD, 20));
        textLabel.setForeground(Color.BLACK);
        textLabel.setBackground(Color.WHITE);
        textLabel.setOpaque(true);
        textLabel.setBorder(new EmptyBorder(30, 30, 30, 30));
        dialog.add(textLabel, BorderLayout.CENTER);
        dialog.setSize(600, 180);
        dialog.setLocationRelativeTo(null);

        final int[] i = {0};
        Timer timer = new Timer(5500, null);
        timer.addActionListener(e -> {
            if (i[0] < cutsceneLines.length) {
                textLabel.setText(cutsceneLines[i[0]++].replace("~", ""));
            } else {
                timer.stop();
                dialog.dispose();

                // Launch game only after cutscene
                SwingUtilities.invokeLater(() -> {
                    FishIsland game = new FishIsland();
                    game.frame.setVisible(true);
                });
            }
        });
        timer.setInitialDelay(0);
        timer.start();
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FishIsland::showIntroCutsceneThenStartGame);
    }
}

class Node {
    String depthName;
    Node left;
    Node right;

    public Node(String depthName) {
        this.depthName = depthName;
    }
}

class BinaryTree {
    Node root;

    public BinaryTree() {
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

    public final void insert(String depthName) {
        root = insertRec(depthName, root);
    }

    private Node insertRec(String depthName, Node node) {
        if (node == null) return new Node(depthName);
        if (depthName.compareTo(node.depthName) > 0) node.left = insertRec(depthName, node.left);
        else if (depthName.compareTo(node.depthName) < 0) node.right = insertRec(depthName, node.right);
        return node;
    }
}
