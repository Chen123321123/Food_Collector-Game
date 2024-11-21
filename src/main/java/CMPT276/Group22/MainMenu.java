package CMPT276.Group22;

import javax.swing.*;
import java.awt.*;

/**
 * The main menu interface for the Dungeon Chef game.
 * Provides options to start game, view instructions, and exit.
 */
public class MainMenu extends JFrame {
    private JPanel menuPanel;
    private JLabel titleLabel;
    private JButton startGameButton;
    private JButton instructionButton;
    private JButton exitButton;
    private SoundManager soundManager;

    public MainMenu() {
        // Set up the frame
        soundManager = SoundManager.getInstance();
        setTitle("Dungeon Chef");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        soundManager.playBackgroundMusic("menu_music");

        // Background image
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/assets/character/Designer.jpeg")));
        background.setLayout(new BorderLayout());
        add(background);

        // Menu container
        menuPanel = new JPanel();
        menuPanel.setOpaque(false); // Make the panel transparent
        menuPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing around components

        // Title
        titleLabel = new JLabel("DUNGEON CHEF");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);

        // Start Game button with image
        startGameButton = createButtonWithImage("/assets/character/start-game.png", 200, 60);
        startGameButton.addActionListener(e -> {    // MODIFIED LINE
            SoundManager.getInstance().playSound("button_click");    // NEW LINE
            startGame();
        });

        // Instruction button with image
        instructionButton = createButtonWithImage("/assets/character/instructions.png", 200, 60);
        instructionButton.addActionListener(e -> {    // MODIFIED LINE
            SoundManager.getInstance().playSound("button_click");    // NEW LINE
            showInstructions();
        });

        // Exit Game button with image
        exitButton = createButtonWithImage("/assets/character/exit-game.png", 200, 60);
        exitButton.addActionListener(e -> {    // MODIFIED LINE
            SoundManager.getInstance().playSound("button_click");    // NEW LINE
            System.exit(0);
        });

        // Add components to menuPanel with GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        menuPanel.add(titleLabel, gbc);

        gbc.gridy = 1; // Move to next row
        menuPanel.add(startGameButton, gbc);

        gbc.gridy = 2; // Move to next row
        menuPanel.add(instructionButton, gbc);

        gbc.gridy = 3; // Move to next row
        menuPanel.add(exitButton, gbc);

        // Add menu panel to background
        background.add(menuPanel, BorderLayout.CENTER);
    }

    private JButton createButtonWithImage(String imagePath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton button = new JButton(scaledIcon);
        button.setPreferredSize(new Dimension(width, height)); // Set preferred button size
        button.setFocusPainted(false); // Remove focus border
        button.setContentAreaFilled(false); // Transparent background
        button.setBorderPainted(false); // Remove button border
        return button;
    }

    private void startGame() {
        this.setVisible(false); // Hide the main menu
        LevelSelectionMenu levelSelectionMenu = new LevelSelectionMenu();
        levelSelectionMenu.setVisible(true); // Show the level selection menu
    }

    private void showInstructions() {
        String instructions = "Welcome to Dungeon Chef!\n\n" +
                "Objective:\nFor each level, collect all ingredients to earn points and complete the recipe. \nAvoid enemies which causes you to loose points. \nIf a moving enemy chatches up to you, game over! \nIf you loose too many point, game over!\n" + "(Look out for bonus ingredients!!)\n\n" +
                "Controls:\n" +
                "- Use arrow keys to move.\n" +
                "Good luck!";
        JOptionPane.showMessageDialog(this, instructions, "Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void dispose() {
        SoundManager.getInstance().stopBackgroundMusic();
        super.dispose();
    }
}
