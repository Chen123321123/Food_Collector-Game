package CMPT276.Group22;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameBoardTest {
    private GameBoard gameBoard;
    private Board mockBoard;

    @BeforeEach
    void setUp() {
        // Mock the Board to avoid dependency on actual game logic
        mockBoard = mock(Board.class);
        when(mockBoard.getCurrentBoardType()).thenReturn("fire");
        when(mockBoard.getRequiredIngredients()).thenReturn(java.util.Arrays.asList("broth", "chili", "meat"));
        
        // Mock the BoardFactory to return our mockBoard
        BoardFactory boardFactory = mock(BoardFactory.class);
        BoardFactory.setInstance(boardFactory);
        when(boardFactory.createBoard(anyString())).thenReturn(mockBoard);

        // Create an instance of GameBoard with the mocked Board
        gameBoard = new GameBoard("fire");
        gameBoard.currentBoard = mockBoard; // Inject the mock board
    }

    @AfterEach
    void tearDown() {
        if (gameBoard != null) {
            gameBoard.dispose();
        }
    }

    @Test
    void testGameBoardInitialization() {
        assertNotNull(gameBoard, "GameBoard should be initialized");
        assertEquals("Dungeon Chef", gameBoard.getTitle(), "Game title should be 'Dungeon Chef'");
    }

    @Test
    void testInitializeTopPanel() {
        JPanel topPanel = gameBoard.topPanel;
        assertNotNull(topPanel, "Top panel should be initialized");
        assertEquals(FlowLayout.class, topPanel.getLayout().getClass(), "Top panel should have FlowLayout");
        assertEquals(3, topPanel.getComponentCount(), "Top panel should contain three components");
    }

    @Test
    void testInitializeGameStatsPanel() {
        JPanel gameStatsPanel = gameBoard.gameStatsPanel;
        assertNotNull(gameStatsPanel, "Game stats panel should be initialized");
        assertEquals(4, gameStatsPanel.getComponentCount(), "Game stats panel should contain labels and spacers");
    }

    @Test
    void testInitializeIngredientsPanel() {
        JPanel ingredientsPanel = gameBoard.ingredientsPanel;
        assertNotNull(ingredientsPanel, "Ingredients panel should be initialized");
        assertEquals(3, ingredientsPanel.getComponentCount(), "Ingredients panel should contain tracking panel, spacer, and visualization panel");
    }

    @Test
    void testUpdateTimerDisplay() {
        gameBoard.secondsElapsed = 125; // 2 minutes and 5 seconds
        SwingUtilities.invokeLater(() -> {
            gameBoard.updateTimerDisplay();
            assertEquals("Time: 2:05", gameBoard.timerLabel.getText(), "Timer label should display 'Time: 2:05'");
        });
    }

    @Test
    void testUpdateScoreDisplay() {
        when(mockBoard.playerScore).thenReturn(50);
        SwingUtilities.invokeLater(() -> {
            gameBoard.updateScoreDisplay();
            assertEquals("Score: 50", gameBoard.scoreLabel.getText(), "Score label should display 'Score: 50'");
        });
    }

    @Test
    void testInitializeTimer() {
        assertNotNull(gameBoard.gameTimer, "Game timer should be initialized");
        assertTrue(gameBoard.gameTimer.isRunning(), "Game timer should be running");
    }

    @Test
    void testElapsedTimeInSeconds() {
        gameBoard.secondsElapsed = 30;
        assertEquals(30, gameBoard.elapsedTimeInSeconds(), "Elapsed time should be 30 seconds");
    }

    @Test
    void testShowGameOverDialog() {
        // Mock the game timer
        gameBoard.gameTimer = mock(javax.swing.Timer.class);
        gameBoard.gameOverDialogShown = false;

        SwingUtilities.invokeLater(() -> {
            gameBoard.showGameOverDialog();
            verify(gameBoard.gameTimer, times(1)).stop();
            assertTrue(gameBoard.gameOverDialogShown, "Game over dialog should be shown");
        });
    }

    @Test
    void testShowRetryButton() {
        // Mock the game timer
        gameBoard.gameTimer = mock(javax.swing.Timer.class);
        gameBoard.showRetryButton();

        verify(gameBoard.gameTimer, times(1)).stop();
        assertTrue(gameBoard.retryButton.isVisible(), "Retry button should be visible");
    }

    @Test
    void testResetGame() {
        // Mock methods
        gameBoard.gameTimer = mock(javax.swing.Timer.class);
        doNothing().when(gameBoard.gameTimer).restart();

        gameBoard.secondsElapsed = 100;
        gameBoard.retryButton.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            gameBoard.resetGame();
            verify(gameBoard.gameTimer, times(1)).restart();
            assertEquals(0, gameBoard.secondsElapsed, "Seconds elapsed should be reset to 0");
            assertFalse(gameBoard.retryButton.isVisible(), "Retry button should not be visible");
        });
    }

    @Test
    void testShowMenu() {
        // Mock JOptionPane
        JOptionPane pane = mock(JOptionPane.class);
        mockStatic(JOptionPane.class);
        when(JOptionPane.showOptionDialog(any(), any(), any(), anyInt(), anyInt(), any(), any(), any())).thenReturn(0);

        // Mock game timer
        gameBoard.gameTimer = mock(javax.swing.Timer.class);

        gameBoard.showMenu();

        verify(gameBoard.gameTimer, times(1)).stop();
        // Since choice is 0 (Resume), timer should start again
        verify(gameBoard.gameTimer, times(1)).start();
    }

    @Test
    void testPauseTimer() {
        // Mock game timer
        gameBoard.gameTimer = mock(javax.swing.Timer.class);
        gameBoard.pauseTimer();

        verify(gameBoard.gameTimer, times(1)).stop();
        // Assuming SoundManager is correctly mocked
    }

    @Test
    void testResumeTimer() {
        // Mock game timer
        gameBoard.gameTimer = mock(javax.swing.Timer.class);
        gameBoard.resumeTimer();

        verify(gameBoard.gameTimer, times(1)).start();
        // Assuming SoundManager is correctly mocked
    }

    @Test
    void testDispose() {
        // Mock SoundManager
        SoundManager soundManager = mock(SoundManager.class);
        SoundManager.setInstance(soundManager);

        gameBoard.dispose();

        verify(soundManager, times(1)).stopBackgroundMusic();
    }

    @Test
    void testUpdateIngredientDisplay() {
        Map<String, Integer> collectedCounts = new HashMap<>();
        collectedCounts.put("broth", 1);
        collectedCounts.put("chili", 2);
        collectedCounts.put("meat", 3);

        SwingUtilities.invokeLater(() -> {
            gameBoard.updateIngredientDisplay(collectedCounts);
            Component[] components = gameBoard.ingredientTrackingPanel.getComponents();
            assertEquals(6, components.length, "Ingredient tracking panel should have labels and spacers");
            // Further assertions can be made on the labels
        });
    }

    @Test
    void testAddIngredientLabel() {
        SwingUtilities.invokeLater(() -> {
            gameBoard.addIngredientLabel("Broth", 1, 3);
            Component[] components = gameBoard.ingredientTrackingPanel.getComponents();
            assertEquals(2, components.length, "Should have label and spacer");
            JLabel label = (JLabel) components[0];
            assertEquals("Broth: 1/3", label.getText(), "Label should display correct ingredient count");
        });
    }

    @Test
    void testGetRecipeVisualizationPanel() {
        RecipeVisualizationPanel panel = gameBoard.getRecipeVisualizationPanel();
        assertNotNull(panel, "RecipeVisualizationPanel should be retrieved");
    }
}

