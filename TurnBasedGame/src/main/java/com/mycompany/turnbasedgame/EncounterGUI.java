// EncounterGUI.java
package com.mycompany.turnbasedgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class EncounterGUI {
    private final JFrame parentFrame;
    private JFrame frame;
    private JTextArea battleLog;
    private JButton attackButton, stunButton, skipButton;
    private Hero player;
    private Monster bot;

    public EncounterGUI(JFrame parentFrame, Hero player, Monster bot) {
        this.parentFrame = parentFrame;
        this.player = player;
        this.bot = bot;
        parentFrame.setVisible(false);
        initUI();
        startBattle();
    }

    private void initUI() {
        frame = new JFrame("⚔️ Battle");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout(10,10));

        battleLog = new JTextArea(12, 40);
        battleLog.setEditable(false);
        battleLog.setFont(new Font("Consolas", Font.PLAIN, 16));
        battleLog.setBackground(Color.WHITE);
        battleLog.setForeground(Color.BLACK);
        frame.add(new JScrollPane(battleLog), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        ActionListener attack = e -> { player.performAction("attack", bot, this::log); afterPlayerTurn(); };
        ActionListener stun   = e -> { player.performAction("stun",   bot, this::log); afterPlayerTurn(); };
        ActionListener skip   = e -> { player.performAction("skip",   bot, this::log); afterPlayerTurn(); };

        attackButton = makeButton("Attack", attack);
        stunButton   = makeButton("Stun",   stun);
        skipButton   = makeButton("Skip",   skip);
        btnPanel.add(attackButton);
        btnPanel.add(stunButton);
        btnPanel.add(skipButton);
        frame.add(btnPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(parentFrame);
        frame.setVisible(true);
    }

    private JButton makeButton(String text, ActionListener al) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 18));
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        b.addActionListener(al);
        return b;
    }

    private void startBattle() {
        log("Battle starts: " + player.playerName + " vs " + bot.playerName);
        if (player.playerSpeed >= bot.playerSpeed) {
            log("Your turn. Choose action.");
        } else {
            botTurn();
        }
    }

    private void afterPlayerTurn() {
        if (bot.isDefeated()) {
            log("🎉 You won!");
            endEncounterWithOption("Congratulations! You won! Return to menu?");
        } else {
            botTurn();
        }
    }

    private void botTurn() {
        bot.actionInputSelection(player, this::log);
        if (player.isDefeated()) {
            log("💀 You died...");
            endEncounterWithOption("You lost. Return to menu?");
        } else {
            log("Your turn again.");
        }
    }

    private void endEncounterWithOption(String message) {
        disableButtons();
        int choice = JOptionPane.showConfirmDialog(
            frame,
            message,
            "Battle Over",
            JOptionPane.YES_NO_OPTION
        );
        frame.dispose();
        if (choice == JOptionPane.YES_OPTION) {
            parentFrame.setVisible(true);
        } else {
            System.exit(0);
        }
    }

    private void disableButtons() {
        attackButton.setEnabled(false);
        stunButton.setEnabled(false);
        skipButton.setEnabled(false);
    }

    private void log(String msg) {
        battleLog.append(msg + "\n");
        battleLog.setCaretPosition(battleLog.getDocument().getLength());
    }

    public static void startEncounter(Hero hero, Monster m) {
        JFrame parentFrame = TurnBasedGame.game.frame;
        SwingUtilities.invokeLater(() -> new EncounterGUI(parentFrame, hero, m));
    }
}
