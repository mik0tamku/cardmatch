import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;

public class mainMenu extends JFrame {
    private JPanel panel;
    private CardButton[] buttons;
    private int[] buttonPairs;
    private CardButton firstButton;

    public mainMenu() {
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

            backIcon = new ImageIcon("/Users/mikolajtamkun/IdeaProjects/cardmatch/card_match/images"); // image source
            frontIcon = new ImageIcon("image." + (pairIndex + 1) + ".jpeg"); // cycles through photos for cards


            setIcon(backIcon);
        }

        public int getPairIndex() {
            return pairIndex;
        }

        public void setPairIndex(int pairIndex) {
            this.pairIndex = pairIndex;
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
                new mainMenu();
            }
        });
    }
}
