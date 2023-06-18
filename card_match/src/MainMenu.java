import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainMenu extends JFrame {
    private JPanel panel;
    private CardButton[] buttons;
    private int[] buttonPairs;
    private CardButton firstButton;

    public MainMenu() {
        panel = new JPanel(new GridLayout(3, 4));
        buttonPairs = new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5}; // Match pairs for each button

        buttons = new CardButton[buttonPairs.length];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new CardButton(i);
            buttons[i].setSize(80, 80);
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CardButton button = (CardButton) e.getSource();
                    revealCard(button);
                }
            });
            panel.add(buttons[i]);
        }

        // Shuffle the buttons randomly
        shuffleButtons();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        pack();
        setVisible(true);
    }

    private void shuffleButtons() {
        List<Integer> indices = new ArrayList<>();

        // Create a list of indices for matching pairs
        for (int i = 0; i < buttonPairs.length; i++) {
            indices.add(i);
        }

        // Shuffle the indices randomly
        Collections.shuffle(indices);

        // Assign the shuffled indices to the buttons
        for (int i = 0; i < buttons.length; i++) {
            int index = indices.get(i);
            buttons[i].setPairIndex(buttonPairs[index]);
            buttons[i].setFrontImagePath("out/production/card_match/images/image" + buttonPairs[index] + "_" + (i % 2 + 1) + ".jpg"); // Assign unique front image path to each button
        }
    }

    private void revealCard(CardButton button) {
        button.showCard();

        if (firstButton == button) {
            return; // Clicked the same button, do nothing
        }

        if (firstButton != null) {
            // Second card clicked, check for a match
            if (firstButton.getPairIndex() == button.getPairIndex()) {
                JOptionPane.showMessageDialog(this, "Match!");
                firstButton.setEnabled(false);
                button.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Not a match!");
                firstButton.hideCard();
                button.hideCard();
            }

            firstButton = null; // Reset first card
        } else {
            // First card clicked
            firstButton = button;
        }
    }

    private class CardButton extends JButton {
        private int pairIndex;
        private ImageIcon backIcon;
        private ImageIcon frontIcon;
        private boolean cardShown;

        public CardButton(int pairIndex) {
            this.pairIndex = pairIndex;
            this.cardShown = false;

            backIcon = new ImageIcon("out/production/card_match/images/back.jpg"); // Path to the back image
            frontIcon = null; // Assign front icon later

            setIcon(backIcon);
        }

        public int getPairIndex() {
            return pairIndex;
        }

        public void setPairIndex(int pairIndex) {
            this.pairIndex = pairIndex;
        }

        public void setFrontImagePath(String frontImagePath) {
            this.frontIcon = new ImageIcon(frontImagePath);
        }

        public void showCard() {
            if (!cardShown) {
                setIcon(frontIcon);
                cardShown = true;
            }
        }

        public void hideCard() {
            if (cardShown) {
                setIcon(backIcon);
                cardShown = false;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainMenu();
            }
        });
    }
}
