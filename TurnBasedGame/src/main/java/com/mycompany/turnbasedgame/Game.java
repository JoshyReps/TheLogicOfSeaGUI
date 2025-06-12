package com.mycompany.turnbasedgame;

import com.mycompany.turnbasedgame.Locations.Location;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Game {

    private static Hero player;
    public static JFrame frame;
    private JTextArea outputArea;

    public Game(Hero player1) {
        player = player1;
        initUI();
    }

    private void initUI() {
        frame = new JFrame("Turn-Based Game GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        outputArea.setBackground(Color.WHITE);
        outputArea.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        JButton travelButton = new JButton("Travel");
        JButton mapButton    = new JButton("Map");
        JButton fightButton  = new JButton("Fight Random");
        JButton exitButton   = new JButton("Exit");

        travelButton.addActionListener(e -> travel());
        mapButton.addActionListener(e -> showMap());
        fightButton.addActionListener(e -> EncounterGUI.startEncounter(
                player,
                new Monster(100, "Random Bot (BOT)", 10, 1, 50)
        ));
        exitButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(travelButton);
        buttonPanel.add(mapButton);
        buttonPanel.add(fightButton);
        buttonPanel.add(exitButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        appendText("Hello Gamer: \"" + player.playerName + "\"!\nWhat would you like to do?");
    }

    private void appendText(String text) {
        outputArea.append(text + "\n");
    }

    private void showMap() {
        String mapText = String.format(
            "-------------- %s's Travel Map! --------------\n\n" +
            "Area Coordinates :\n\n" +
            "[0, 0] -> Spawn / Foosha Village\n" +
            "[3, 12] -> Sky Island\n" +
            "[-3, -12] -> Fish Island\n" +
            "[15, 15] -> Grand Line\n" +
            "[25, 25] -> The Land Of Wano\n",
            player.playerName
        );
        JOptionPane.showMessageDialog(frame, mapText);
    }

    private void travel() {
        JPanel movementPanel = new JPanel(new BorderLayout());
        movementPanel.setBackground(Color.WHITE);

        JTextArea movementOutput = new JTextArea(10, 30);
        movementOutput.setEditable(false);
        movementOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        movementOutput.setBackground(Color.WHITE);
        movementOutput.setForeground(Color.BLACK);

        // initial display
        updateMovementOutput(movementOutput);

        JPanel controls = new JPanel(new FlowLayout());
        controls.setBackground(Color.WHITE);

        JButton north = new JButton("↑");
        JButton west  = new JButton("←");
        JButton south = new JButton("↓");
        JButton east  = new JButton("→");
        JButton back  = new JButton("Back");

        ActionListener movementAction = e -> {
            switch (e.getActionCommand()) {
                case "↑" -> player.playerPosition[1]++;
                case "←" -> player.playerPosition[0]--;
                case "↓" -> player.playerPosition[1]--;
                case "→" -> player.playerPosition[0]++;
            }
            updateMovementOutput(movementOutput);
            String posStr = Arrays.toString(player.playerPosition);
            Location loc = Location.getLocation(posStr);
            if (loc != null) {
                frame.setVisible(false);
                loc.start();
            }
        };

        north.addActionListener(movementAction);
        west.addActionListener(movementAction);
        south.addActionListener(movementAction);
        east.addActionListener(movementAction);
        back.addActionListener(e -> ((Window) movementPanel.getTopLevelAncestor()).dispose());

        controls.add(north);
        controls.add(west);
        controls.add(south);
        controls.add(east);
        controls.add(back);

        movementPanel.add(new JScrollPane(movementOutput), BorderLayout.CENTER);
        movementPanel.add(controls, BorderLayout.SOUTH);

        JDialog dialog = new JDialog(frame, "Travel", true);
        dialog.setContentPane(movementPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void updateMovementOutput(JTextArea textarea) {
        String pos = Arrays.toString(player.playerPosition);
        String name = Location.getLocationName(pos);
        textarea.setText(String.format("Location: %s\nPosition: %s", name, pos));
    }

    public static void main(String[] args) {
        Hero hero = new Hero(100, "Luffy", 20, 10, 12);
        SwingUtilities.invokeLater(() -> new Game(hero));
    }
}
