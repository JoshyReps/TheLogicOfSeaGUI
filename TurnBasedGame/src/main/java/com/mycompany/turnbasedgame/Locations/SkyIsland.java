package com.mycompany.turnbasedgame.Locations;

import com.mycompany.turnbasedgame.EncounterGUI;
import com.mycompany.turnbasedgame.Monster;
import com.mycompany.turnbasedgame.TurnBasedGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SkyIsland extends Location{

    private final JFrame frame;
    private final JTextArea outputArea;
    private final JPanel cloudButtonPanel;
    private int currentLevel = 0;

    // Cloud structure (true = correct cloud)
    private final boolean[][] cloudsChoices2D = {
        {true, false},
        {true, false, false},
        {false, false, true, false},
        {false, false, false, true, false},
        {false, true, false, false, false, false}
    };
    
    {
        frame = new JFrame("☁️ Sky Island Adventure");
        outputArea = new JTextArea(6, 50);
        cloudButtonPanel = new JPanel();
    }

    public void start () {
        
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(Color.WHITE);

        // Output Area
        
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        outputArea.setForeground(Color.BLACK);
        outputArea.setBackground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Cloud Buttons Panel
        
        cloudButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        cloudButtonPanel.setBackground(Color.WHITE);
        frame.add(cloudButtonPanel, BorderLayout.SOUTH);

        printIntro();
        showCutscene();
        updateCloudButtons();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void appendMessage(String msg) {
        outputArea.append(msg + "\n");
    }

    private void printIntro() {
        appendMessage("🧗 Current Objective: Reach The Top of Sky Island!");
        appendMessage("☁️ Try not to fall down... :)\n");
    }

    private void updateCloudButtons() {
        cloudButtonPanel.removeAll();
        boolean[] currentClouds = cloudsChoices2D[currentLevel];
        appendMessage(String.format("🏔️ Level %d", currentLevel + 1));

        for (int i = 0; i < currentClouds.length; i++) {
            JPanel cloudContainer = new JPanel(new BorderLayout());
            cloudContainer.setBackground(Color.WHITE);

            // 🔁 Replace with your actual image path
            ImageIcon cloudIcon = new ImageIcon("C:\\Users\\MARIFER\\Documents\\NetBeansProjects\\TurnBasedGame\\src\\main\\java\\skyIslandImages\\cloud.png");
            Image image = cloudIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel cloudLabel = new JLabel(new ImageIcon(image));
            cloudLabel.setHorizontalAlignment(SwingConstants.CENTER);

            final int index = i;
            cloudLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cloudLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (currentClouds[index]) {
                        appendMessage("✅ Correct! You jumped on a stable cloud.");
                        currentLevel++;
                        if (currentLevel == cloudsChoices2D.length) {
                            frame.dispose();
                            showBossBattle();
                            return;
                        }
                    } else {
                        appendMessage("💨 Womp Womp! That cloud was too soft!");
                        if (currentLevel > 0) currentLevel--;
                    }
                    updateCloudButtons();
                }
            });

            JLabel indexLabel = new JLabel("Cloud " + i, SwingConstants.CENTER);
            indexLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            indexLabel.setForeground(Color.BLACK);

            cloudContainer.add(cloudLabel, BorderLayout.CENTER);
            cloudContainer.add(indexLabel, BorderLayout.SOUTH);
            cloudButtonPanel.add(cloudContainer);
        }

        cloudButtonPanel.revalidate();
        cloudButtonPanel.repaint();
    }

    private void showCutscene() {
        String[] cutsceneLines = {
            "........",
            "It would seem that~",
            "You have ventured to a different location~",
            "The island seems to be in the sky~",
            "We need to get up there somehow! :P"
        };

        JDialog dialog = new JDialog(frame, "Sky Island Cutscene", true);
        JLabel textLabel = new JLabel("", SwingConstants.CENTER);
        textLabel.setFont(new Font("Serif", Font.BOLD, 20));
        textLabel.setForeground(Color.BLACK);
        textLabel.setBackground(Color.WHITE);
        textLabel.setOpaque(true);
        textLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        dialog.add(textLabel);
        dialog.setSize(600, 180);
        dialog.setLocationRelativeTo(frame);

        final int[] i = {0};
        Timer timer = new Timer(2500, null);
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

    private void showBossBattle() {
        String[] bossDialog = {
            "........",
            "*Boss*: That was certainly impressive...",
            "*Boss*: You somehow managed to get on top...",
            "*Boss*: NOW YOU MUST PERISH!!! BAHAHAHAHA!!!"
        };

        JDialog bossDialogBox = new JDialog(frame, "⚡ Boss Appears!", true);
        JLabel bossText = new JLabel("", SwingConstants.CENTER);
        bossText.setFont(new Font("Monospaced", Font.BOLD, 18));
        bossText.setForeground(Color.BLACK);
        bossText.setBackground(Color.WHITE);
        bossText.setOpaque(true);
        bossText.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        bossDialogBox.add(bossText);
        bossDialogBox.setSize(600, 180);
        bossDialogBox.setLocationRelativeTo(frame);

        final int[] i = {0};
        Timer timer = new Timer(2500, null);
        timer.addActionListener(e -> {
            if (i[0] < bossDialog.length) {
                bossText.setText(bossDialog[i[0]++]);
            } else {
                timer.stop();
                bossDialogBox.dispose();
                JOptionPane.showMessageDialog(frame,
                    "⚡ BOSS BATTLE: Enel Zues has appeared!\nPrepare for lightning!",
                    "Boss Fight!",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        timer.setInitialDelay(0);
        timer.start();
        bossDialogBox.setVisible(true);
        
        EncounterGUI.startEncounter(TurnBasedGame.hero, new Monster(100, "Enel Zues: (Sky Island Boss)", 15, 5, 200));
    }

    public static void main(String[] args) {
        new SkyIsland().start();
    }
}