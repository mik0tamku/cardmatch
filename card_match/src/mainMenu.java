import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;

public class mainMenu extends JFrame {
    private JPanel panel;
    private JButton[] buttons;
    private ImageIcon[] buttonIcons;
    private int[] buttonPairs;
    private int firstButtonIndex;

    public mainMenu() {
        panel = new JPanel(new GridLayout(3, 4));
        buttonPairs = new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5}; // Match pairs for each button
        buttonIcons = loadButtonIcons(".jpeg");

        buttons = new JButton[buttonPairs.length];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setSize(80, 80);
            buttons[i].setActionCommand(Integer.toString(i));
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int buttonIndex = Integer.parseInt(e.getActionCommand());
                    revealCard(buttonIndex);
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

    private ImageIcon[] loadButtonIcons(String imageFormat) {
        ImageIcon[] icons = new ImageIcon[buttonPairs.length];
        List<Integer> indices = new ArrayList<>();

        // Create a list of indices for matching pairs
        for (int i = 0; i < buttonPairs.length; i++) {
            indices.add(i);
        }

        // Shuffle the indices randomly
        Collections.shuffle(indices);

        // Load the images and assign them to buttons based on the shuffled indices
        for (int i = 0; i < buttonPairs.length; i++) {
            int index = indices.get(i);
            String imagePath = "image" + (index + 1) + "." + imageFormat; // image files are named as image1.png, image2.png
            icons[i] = new ImageIcon(imagePath);
        }

        return icons;
    }

    private void shuffleButtons() {
        List<ImageIcon> iconList = new ArrayList<>();

        // Add two copies of each button icon to the list
        for (int i = 0; i < buttonPairs.length; i++) {
            iconList.add(buttonIcons[i]);
            iconList.add(buttonIcons[i]);
        }

        // Shuffle the icons randomly
        Collections.shuffle(iconList);

        // Assign the shuffled icons to the buttons
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setIcon(iconList.get(i));
        }
    }

    private void revealCard(int buttonIndex) {
        buttons[buttonIndex].setIcon(buttonIcons[buttonIndex]);

        if (firstButtonIndex == buttonIndex) {
            return; // Clicked the same button, do nothing
        }

        if (firstButtonIndex != -1) {
            // Second card clicked, check for a match
            if (buttonPairs[firstButtonIndex] == buttonPairs[buttonIndex]) {
                JOptionPane.showMessageDialog(this, "Match!");
                buttons[firstButtonIndex].setEnabled(false);
                buttons[buttonIndex].setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Not a match!");
                buttons[firstButtonIndex].setIcon(null);
                buttons[buttonIndex].setIcon(null);
            }

            firstButtonIndex = -1; // Reset first card index
        } else {
            // First card clicked
            firstButtonIndex = buttonIndex;
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
