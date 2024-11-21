package CMPT276.Group22;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.timing.Timeout;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import javax.swing.*;

import static org.mockito.Mockito.*;

class MainMenuTest {

    private FrameFixture window;
    private Robot robot;

    @BeforeAll
    static void setUpOnce() {
        // Install a repaint manager to catch EDT violations
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    void setUp() {
        // Initialize the robot
        robot = BasicRobot.robotWithNewAwtHierarchy();

        // Mock SoundManager
        SoundManager soundManagerMock = Mockito.mock(SoundManager.class);
        SoundManager.setInstance(soundManagerMock);

        // Create an instance of MainMenu
        SwingUtilities.invokeLater(() -> {
            MainMenu frame = new MainMenu();
            frame.setVisible(true);
        });

        // Find the frame and wrap it in a FrameFixture
        window = WindowFinder.findFrame(MainMenu.class).using(robot);
    }

    @AfterEach
    void tearDown() {
        if (window != null) {
            window.cleanUp();
        }
    }

    @Test
    void testMainMenuInitialization() {
        window.requireTitle("Dungeon Chef");
        window.requireVisible();
    }

    @Test
    void testStartGameButtonAction() {
        // Find the Start Game button by its image or position
        JButtonFixture startGameButton = findButtonByImage("/assets/character/start-game.png");
        assertNotNull(startGameButton, "Start Game button should be found");

        // Click the Start Game button
        startGameButton.click();

        // Verify that the button click sound is played
        SoundManager soundManager = SoundManager.getInstance();
        verify(soundManager, times(1)).playSound("button_click");

        // Check that LevelSelectionMenu is displayed
        FrameFixture levelSelectionWindow = WindowFinder.findFrame(LevelSelectionMenu.class)
                .withTimeout(Timeout.timeout(5000))
                .using(robot);
        levelSelectionWindow.requireVisible();

        // Clean up
        levelSelectionWindow.cleanUp();
    }

    @Test
    void testInstructionButtonAction() {
        // Find the Instruction button by its image or position
        JButtonFixture instructionButton = findButtonByImage("/assets/character/instructions.png");
        assertNotNull(instructionButton, "Instruction button should be found");

        // Click the Instruction button
        instructionButton.click();

        // Verify that the button click sound is played
        SoundManager soundManager = SoundManager.getInstance();
        verify(soundManager, times(1)).playSound("button_click");

        // Check that an instruction dialog is shown
        DialogFixture dialog = WindowFinder.findDialog("Instructions")
                .withTimeout(Timeout.timeout(5000))
                .using(robot);
        dialog.requireVisible();
        dialog.requireTitle("Instructions");

        // Close the dialog
        dialog.button(new JButtonMatcher("OK")).click();
    }

    @Test
    void testExitButtonAction() {
        // Mock System.exit
        SystemExitManager exitManager = new SystemExitManager();
        exitManager.setUp();

        try {
            // Find the Exit button by its image or position
            JButtonFixture exitButton = findButtonByImage("/assets/character/exit-game.png");
            assertNotNull(exitButton, "Exit button should be found");

            // Click the Exit button
            exitButton.click();

            // Verify that the button click sound is played
            SoundManager soundManager = SoundManager.getInstance();
            verify(soundManager, times(1)).playSound("button_click");

            // Verify that System.exit(0) was called
            assertTrue(exitManager.isExitCalled(), "System.exit should have been called");
            assertEquals(0, exitManager.getStatusCode(), "Exit status code should be 0");
        } finally {
            exitManager.tearDown();
        }
    }

    // Helper method to find a button by its image path
    private JButtonFixture findButtonByImage(String imagePath) {
        return window.button(new ButtonImageMatcher(imagePath));
    }

    // Custom matcher to find buttons by image
    private static class ButtonImageMatcher extends org.assertj.swing.core.GenericTypeMatcher<JButton> {
        private final String imagePath;

        public ButtonImageMatcher(String imagePath) {
            super(JButton.class);
            this.imagePath = imagePath;
        }

        @Override
        protected boolean isMatching(JButton button) {
            Icon icon = button.getIcon();
            if (icon instanceof ImageIcon) {
                ImageIcon imageIcon = (ImageIcon) icon;
                String iconPath = imageIcon.getDescription();
                return iconPath != null && iconPath.endsWith(imagePath);
            }
            return false;
        }
    }

    // Custom matcher to find OK button in dialogs
    private static class JButtonMatcher extends org.assertj.swing.core.GenericTypeMatcher<JButton> {
        private final String text;

        public JButtonMatcher(String text) {
            super(JButton.class);
            this.text = text;
        }

        @Override
        protected boolean isMatching(JButton button) {
            return text.equals(button.getText());
        }
    }

    // Utility class to mock System.exit calls
    private static class SystemExitManager {
        private SecurityManager originalSecurityManager;
        private boolean exitCalled = false;
        private int statusCode;

        public void setUp() {
            originalSecurityManager = System.getSecurityManager();
            System.setSecurityManager(new SecurityManager() {
                @Override
                public void checkPermission(java.security.Permission perm) {
                    // Allow everything except exitVM
                }

                @Override
                public void checkExit(int status) {
                    exitCalled = true;
                    statusCode = status;
                    throw new SecurityException("System.exit attempted");
                }
            });
        }

        public void tearDown() {
            System.setSecurityManager(originalSecurityManager);
        }

        public boolean isExitCalled() {
            return exitCalled;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }
}

