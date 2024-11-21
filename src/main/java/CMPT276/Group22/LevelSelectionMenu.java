package CMPT276.Group22;

import javax.swing.*;
import java.awt.*;

/**
 * Level selection interface allowing players to choose between Fire, Ice, and Earth boards.
 * Presents visual buttons for each level type and handles level initialization.
 */
public class LevelSelectionMenu extends JFrame {
    private JPanel levelPanel;

    public LevelSelectionMenu() {
        // Set up the frame
        setTitle("Select Level");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Background image
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/assets/character/MainMenu.png")));
        background.setLayout(new BorderLayout());
        add(background);

        // Parent panel with GridBagLayout to center the levelPanel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // Transparent to show background

        // Create the level selection panel with BoxLayout for button arrangement
        levelPanel = new JPanel();
        levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.Y_AXIS));
        levelPanel.setOpaque(false); // Transparent to show background

        // Load images and create buttons for each level
        JButton level1Button = createButtonWithImage("/assets/character/fire.png", 200, 60);
        level1Button.addActionListener(e -> startGame("Level 1"));

        JButton level2Button = createButtonWithImage("/assets/character/ice1.png", 200, 60);
        level2Button.addActionListener(e -> startGame("Level 2"));

        JButton level3Button = createButtonWithImage("/assets/character/earth.png", 200, 60);
        level3Button.addActionListener(e -> startGame("Level 3"));

        // Add buttons to the level panel with small spacing
        levelPanel.add(level1Button);
        levelPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Adjust spacing as needed
        levelPanel.add(level2Button);
        levelPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        levelPanel.add(level3Button);

        // Center the levelPanel within centerPanel
        centerPanel.add(levelPanel, new GridBagConstraints());

        // Add the center panel to the background
        background.add(centerPanel, BorderLayout.CENTER);
    }

    private JButton createButtonWithImage(String imagePath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton button = new JButton(scaledIcon);
        button.setPreferredSize(new Dimension(width, height));
        button.setFocusPainted(false); // Remove focus border
        button.setContentAreaFilled(false); // Transparent background
        button.setBorderPainted(false); // Remove button border
        return button;
    }

    private void startGame(String level) {
        SoundManager.getInstance().playSound("button_click");
        SwingUtilities.invokeLater(() -> {
            // Determine the board type based on level selection
            String boardType;
            switch (level) {
                case "Level 1":
                    boardType = "fire";
                    SoundManager.getInstance().playBackgroundMusic("fire_music");
                    break;
                case "Level 2":
                    boardType = "ice";
                    SoundManager.getInstance().playBackgroundMusic("ice_music");
                    break;
                case "Level 3":
                    boardType = "earth";
                    SoundManager.getInstance().playBackgroundMusic("earth_music");
                    break;
                default:
                    boardType = "fire"; // Default to Fire level
                    SoundManager.getInstance().playBackgroundMusic("fire_music");
                    break;
            }

            // Create a new GameBoard instance with the selected board type
            GameBoard gameBoard = new GameBoard(boardType);
            gameBoard.setVisible(true);

            dispose();  // Close the level selection menu
        });
    }
}
