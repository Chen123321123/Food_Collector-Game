package CMPT276.Group22;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.assertj.swing.launcher.ApplicationLauncher.application;

class LevelSelectionMenuTest {

    private FrameFixture window;
    private Robot robot;

    @BeforeEach
    void setUp() {
        // Initialize the robot
        robot = BasicRobot.robotWithNewAwtHierarchy();

        // Create an instance of LevelSelectionMenu
        SwingUtilities.invokeLater(() -> {
            LevelSelectionMenu frame = new LevelSelectionMenu();
            frame.setVisible(true);
        });

        // Find the frame and wrap it in a FrameFixture
        window = WindowFinder.findFrame(LevelSelectionMenu.class).using(robot);
    }

    @AfterEach
    void tearDown() {
        if (window != null) {
            window.cleanUp();
        }
    }

    @Test
    void testLevelSelectionMenuInitialization() {
        window.requireTitle("Select Level");
        window.requireVisible();
    }

    @Test
    void testLevelButtonsExist() {
        // Since buttons have no names, we need to find them by type and order
        JButtonFixture level1Button = window.button(new GenericTypeMatcher<JButton>(JButton.class) {
            int count = 0;

            @Override
            protected boolean isMatching(JButton button) {
                return ++count == 1;
            }
        });

        JButtonFixture level2Button = window.button(new GenericTypeMatcher<JButton>(JButton.class) {
            int count = 0;

            @Override
            protected boolean isMatching(JButton button) {
                return ++count == 2;
            }
        });

        JButtonFixture level3Button = window.button(new GenericTypeMatcher<JButton>(JButton.class) {
            int count = 0;

            @Override
            protected boolean isMatching(JButton button) {
                return ++count == 3;
            }
        });

        level1Button.requireVisible();
        level2Button.requireVisible();
        level3Button.requireVisible();
    }

    @Test
    void testLevel1ButtonAction() {
        // Mock dependencies
        SoundManager soundManagerMock = org.mockito.Mockito.mock(SoundManager.class);
        SoundManager.setInstance(soundManagerMock);

        // Interact with the button
        window.button(new IndexButtonMatcher(0)).click();

        // Verify that the button click sound is played
        org.mockito.Mockito.verify(soundManagerMock).playSound("button_click");

        // Check that GameBoard is displayed
        FrameFixture gameBoardWindow = WindowFinder.findFrame(GameBoard.class).using(robot);
        gameBoardWindow.requireVisible();

        // Clean up
        gameBoardWindow.cleanUp();
    }

    @Test
    void testLevel2ButtonAction() {
        // Mock dependencies
        SoundManager soundManagerMock = org.mockito.Mockito.mock(SoundManager.class);
        SoundManager.setInstance(soundManagerMock);

        // Interact with the button
        window.button(new IndexButtonMatcher(1)).click();

        // Verify that the button click sound is played
        org.mockito.Mockito.verify(soundManagerMock).playSound("button_click");

        // Check that GameBoard is displayed
        FrameFixture gameBoardWindow = WindowFinder.findFrame(GameBoard.class).using(robot);
        gameBoardWindow.requireVisible();

        // Clean up
        gameBoardWindow.cleanUp();
    }

    @Test
    void testLevel3ButtonAction() {
        // Mock dependencies
        SoundManager soundManagerMock = org.mockito.Mockito.mock(SoundManager.class);
        SoundManager.setInstance(soundManagerMock);

        // Interact with the button
        window.button(new IndexButtonMatcher(2)).click();

        // Verify that the button click sound is played
        org.mockito.Mockito.verify(soundManagerMock).playSound("button_click");

        // Check that GameBoard is displayed
        FrameFixture gameBoardWindow = WindowFinder.findFrame(GameBoard.class).using(robot);
        gameBoardWindow.requireVisible();

        // Clean up
        gameBoardWindow.cleanUp();
    }

    // Custom matcher to find buttons by index since they don't have names
    private static class IndexButtonMatcher extends GenericTypeMatcher<JButton> {
        private final int targetIndex;
        private int currentIndex = -1;

        public IndexButtonMatcher(int targetIndex) {
            super(JButton.class);
            this.targetIndex = targetIndex;
        }

        @Override
        protected boolean isMatching(JButton button) {
            currentIndex++;
            return currentIndex == targetIndex;
        }
    }
}
