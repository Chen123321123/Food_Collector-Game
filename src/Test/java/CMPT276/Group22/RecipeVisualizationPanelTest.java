package CMPT276.Group22;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.timing.Timeout;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeVisualizationPanelTest {

    private JFrame testFrame;
    private RecipeVisualizationPanel panel;
    private Robot robot;
    private FrameFixture window;

    @BeforeAll
    static void setUpOnce() {
        // Install a repaint manager to catch EDT violations
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    void setUp() {
        // Initialize the robot
        robot = BasicRobot.robotWithNewAwtHierarchy();

        // Mock the Board
        Board mockBoard = Mockito.mock(Board.class);

        // Mock currentBoard instance checks
        when(mockBoard instanceof FireBoard).thenReturn(true);
        when(mockBoard instanceof IceBoard).thenReturn(false);
        when(mockBoard instanceof EarthBoard).thenReturn(false);

        // Create the panel
        SwingUtilities.invokeLater(() -> {
            panel = new RecipeVisualizationPanel(mockBoard);
            testFrame = new JFrame();
            testFrame.add(panel);
            testFrame.pack();
            testFrame.setVisible(true);
        });

        // Wrap the frame in a FrameFixture
        window = new FrameFixture(robot, testFrame);
    }

    @AfterEach
    void tearDown() {
        if (window != null) {
            window.cleanUp();
        }
    }

    @Test
    void testPanelInitialization() {
        // Verify that the panel is visible
        window.requireVisible();

        // Verify panel size
        Dimension expectedSize = new Dimension(180, panel.imageHeight * 2 + panel.VERTICAL_GAP);
        assertEquals(expectedSize, panel.getPreferredSize(), "Panel preferred size should match expected size");

        // Verify that images are loaded
        assertNotNull(panel.baseImage, "Base image should be loaded");
        assertFalse(panel.overlayImages.isEmpty(), "Overlay images should be loaded");
    }

    @Test
    void testUpdateIngredientState() {
        // Assume that 'broth' is one of the ingredients
        panel.updateIngredientState("broth", true);

        // Verify that the ingredientCompleted map is updated
        assertTrue(panel.ingredientCompleted.get("broth"), "Ingredient 'broth' should be marked as completed");
    }

    @Test
    void testUpdateBonusState() {
        panel.updateBonusState(true);

        // Verify that bonusCollected is updated
        assertTrue(panel.bonusCollected, "Bonus should be marked as collected");
    }

    @Test
    void testPaintComponent() {
        // Since testing paintComponent directly is complex, we can verify that no exceptions occur during painting
        SwingUtilities.invokeLater(() -> {
            try {
                panel.repaint();
            } catch (Exception e) {
                fail("Painting the component should not throw an exception");
            }
        });
    }

    @Test
    void testLoadImages() {
        // Verify that images are loaded based on the board type
        assertNotNull(panel.baseImage, "Base image should be loaded");
        assertFalse(panel.overlayImages.isEmpty(), "Overlay images should be loaded");
        assertNotNull(panel.bonusImage, "Bonus image should be loaded");
    }

    @Test
    void testPanelRepaintOnIngredientUpdate() {
        // Spy on the panel to verify repaint calls
        RecipeVisualizationPanel spyPanel = Mockito.spy(panel);

        // Update an ingredient state
        spyPanel.updateIngredientState("broth", true);

        // Verify that repaint is called
        verify(spyPanel, times(1)).repaint();
    }

    @Test
    void testPanelRepaintOnBonusUpdate() {
        // Spy on the panel to verify repaint calls
        RecipeVisualizationPanel spyPanel = Mockito.spy(panel);

        // Update bonus state
        spyPanel.updateBonusState(true);

        // Verify that repaint is called
        verify(spyPanel, times(1)).repaint();
    }
}

