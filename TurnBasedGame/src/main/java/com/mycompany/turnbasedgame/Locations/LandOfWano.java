package com.mycompany.turnbasedgame.Locations;

import com.mycompany.turnbasedgame.EncounterGUI;
import com.mycompany.turnbasedgame.Monster;
import com.mycompany.turnbasedgame.TurnBasedGame;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class LandOfWano extends Location {

    private JFrame frame;
    private JPanel islandPanel, buttonPanel;
    private java.util.List<JLabel> islandLabels = new ArrayList<>();
    private DoublyLinkedList wanoIslands;
    private DoublyLinkedList.Node currentIsland;
    private Queue<String> quests;
    private JLabel questLabel;

    public void start() {
        showIntroCutscene();
        setupData();
        setupGUI();
    }

    private void showBossScene() {
        
        String[] lines = {
            "*Boss* : YOU FOOL!!!",
            "*Boss* : HOW DARE YOU RUIN MY PERFECT TERRITORY!",
            "*Player* : Is that What I did?",
            "*Boss* : DO NOT THINK YOU'LL GET AWAY!",
            "*Player* : .....",
            "*Player* : I Suppose I'll Just Ruin you As well >:)"
        };
        

        JDialog dialog = new JDialog((Frame) null, "Cutscene", true);
        dialog.setLayout(new BorderLayout());

        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        dialog.add(label, BorderLayout.CENTER);
        dialog.setSize(600, 180);
        dialog.setLocationRelativeTo(null);

        new Thread(() -> {
            for (String line : lines) {
                label.setText(line);
                try {
                    Thread.sleep(2800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            dialog.dispose();
        }).start();

        dialog.setVisible(true);
        EncounterGUI.startEncounter(TurnBasedGame.hero, new Monster( 200, "Yonko Kaido: (The Land Of Wano Boss)", 40, 15, 200));
    }

    private void showIntroCutscene() {
        String[] lines = {
            "........",
            "At last, We have Made it >:)",
            "The Land of Samurais!!! :D",
            "There are so Many Locations Here :O.....",
            "Although, This Country Seems to Be in a lot of Problems :(",
            "Let's Help Them and Defeat the Final Boss :)"
        };

        JDialog dialog = new JDialog((Frame) null, "Boss Scene", true);
        dialog.setLayout(new BorderLayout());

        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setForeground(Color.BLACK);
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        dialog.add(label, BorderLayout.CENTER);
        dialog.setSize(600, 180);
        dialog.setLocationRelativeTo(null);

        new Thread(() -> {
            for (String line : lines) {
                label.setText(line);
                try {
                    Thread.sleep(2800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            dialog.dispose();
        }).start();

        dialog.setVisible(true);
    }

    private void setupData() {
        quests = new LinkedList<>(List.of(
                "Quest 1: Head to `Kuri`",
                "Quest 2: Destroy the Prison `Udon`",
                "Quest 3: Kill the `Sea Beast`",
                "Quest 4: Add a `Bridge` between `Ringo` and `Hakumai`",
                "Quest 5: Add a `Wall of Mountain` before `Kuri` and after `Hakumai`"
        ));

        wanoIslands = new DoublyLinkedList("Reverse Mountain", "Kuri", "Udon", "Kibi", "Sea Beast", "Flower Capital", "Ringo", "Hakumai");
        currentIsland = wanoIslands.head;
    }

    private void setupGUI() {
        frame = new JFrame("Land of Wano Mini Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        islandPanel = new JPanel();
        islandPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        updateIslandDisplay();

        questLabel = new JLabel(quests.peek(), SwingConstants.CENTER);
        questLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(questLabel, BorderLayout.NORTH);

        buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        String[] buttons = {"Left (A)", "Right (D)", "Insert Wall (W)", "Insert Bridge (B)", "Delete (K)", "Next Quest (N)", "Restart (R)", "Exit"};
        char[] commands = {'a', 'd', 'w', 'b', 'k', 'n', 'r', 'x'};

        for (int i = 0; i < buttons.length; i++) {
            JButton btn = new JButton(buttons[i]);
            char action = commands[i];
            btn.addActionListener(e -> handleAction(action));
            buttonPanel.add(btn);
        }

        frame.add(islandPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void updateIslandDisplay() {
        islandPanel.removeAll();
        islandLabels.clear();

        DoublyLinkedList.Node temp = wanoIslands.head;
        while (temp != null) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setPreferredSize(new Dimension(120, 140));

            String imagePath = "C:\\Users\\MARIFER\\Documents\\NetBeansProjects\\TurnBasedGame\\src\\main\\java\\wanoImages\\" + temp.island + ".png";
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(scaled));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel nameLabel = new JLabel(temp.island, SwingConstants.CENTER);
            nameLabel.setOpaque(true);

            if (temp == currentIsland) {
                nameLabel.setBackground(Color.YELLOW);
            }

            panel.add(imageLabel, BorderLayout.CENTER);
            panel.add(nameLabel, BorderLayout.SOUTH);
            islandPanel.add(panel);

            temp = temp.next;
        }

        islandPanel.revalidate();
        islandPanel.repaint();
    }

    private void handleAction(char action) {
        switch (action) {
            case 'a' -> {
                if (currentIsland.prev != null) currentIsland = currentIsland.prev;
            }
            case 'd' -> {
                if (currentIsland.next != null) currentIsland = currentIsland.next;
            }
            case 'w' -> {
                wanoIslands.insert(currentIsland, "Wall of Mountain");
            }
            case 'b' -> {
                wanoIslands.insert(currentIsland, "Bridge");
            }
            case 'k' -> {
                if (currentIsland != wanoIslands.head && currentIsland != wanoIslands.tail) {
                    DoublyLinkedList.Node toDelete = currentIsland;
                    currentIsland = currentIsland.prev;
                    wanoIslands.remove(toDelete);
                }
            }
            case 'n' -> {
                String completed = quests.poll();
                if (completed != null) {
                    JOptionPane.showMessageDialog(frame, completed + " completed!");
                }

                if (quests.isEmpty()) {
                    DoublyLinkedList expectedResult = new DoublyLinkedList(
                            "Reverse Mountain", "Wall of Mountain", "Kuri", "Kibi",
                            "Flower Capital", "Ringo", "Bridge", "Hakumai", "Wall of Mountain"
                    );

                    if (wanoIslands.equals(expectedResult)) {
                        frame.dispose();
                        showBossScene();
                        return;
                    } else {
                        JOptionPane.showMessageDialog(frame, "❌ Incorrect arrangement! Try again.");
                        restartGame();
                        return;
                    }
                }
            }
            case 'r' -> {
                restartGame();
                return;
            }
            case 'x' -> frame.dispose();
        }

        questLabel.setText(quests.peek() != null ? quests.peek() : "All quests completed!");
        updateIslandDisplay();
    }

    private void restartGame() {
        frame.dispose();
        new LandOfWano();
    }
    
    static class DoublyLinkedList {
        Node head, tail;

        class Node {
            String island;
            Node next, prev;

            Node(String island) {
                this.island = island;
            }
        }

        public DoublyLinkedList(String... islands) {
            for (String island : islands) append(island);
        }

        void append(String island) {
            Node node = new Node(island);
            if (head == null) {
                head = tail = node;
            } else {
                tail.next = node;
                node.prev = tail;
                tail = node;
            }
        }

        void insert(Node current, String island) {
            if (current == tail) {
                append(island);
            } else {
                Node newNode = new Node(island);
                Node after = current.next;
                current.next = newNode;
                newNode.prev = current;
                newNode.next = after;
                if (after != null) after.prev = newNode;
            }
        }

        void remove(Node node) {
            if (node == null || node == head || node == tail) return;

            Node before = node.prev;
            Node after = node.next;

            if (before != null) before.next = after;
            if (after != null) after.prev = before;
        }

        boolean equals(DoublyLinkedList other) {
            Node n1 = this.head, n2 = other.head;
            while (n1 != null && n2 != null) {
                if (!n1.island.equals(n2.island)) return false;
                n1 = n1.next;
                n2 = n2.next;
            }
            return n1 == null && n2 == null;
        }
    }
}
