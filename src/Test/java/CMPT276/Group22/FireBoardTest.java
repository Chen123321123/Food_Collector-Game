package CMPT276.Group22;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FireBoardTest {
    private FireBoard fireBoard;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        fireBoard = new FireBoard();
        // Mock methods that load resources to avoid loading actual images and sounds during testing
        fireBoard.images = (HashMap<String, Image>) Mockito.mock(Map.class);
        fireBoard.soundManager = Mockito.mock(SoundManager.class);

        // Initialize necessary fields
        fireBoard.grid = new Entity[FireBoard.GRID_HEIGHT][FireBoard.GRID_WIDTH];
        fireBoard.ingredients.clear();
        fireBoard.staticEnemies.clear();
        fireBoard.movingEnemies.clear();
        fireBoard.barriers.clear();
        fireBoard.collectedIngredients.clear();
        fireBoard.playerX = 0;
        fireBoard.playerY = 0;
    }

    @Test
    void testLoadBoardSpecificImages() {
        fireBoard.loadBoardSpecificImages();
        // Verify that images are loaded (assuming loadImage adds entries to the images map)
        verify(fireBoard.images, atLeastOnce()).put(anyString(), any(Image.class));
    }

    @Test
    void testInitializeIngredients() {
        fireBoard.initializeIngredients();
        // Check that the correct number of ingredients has been initialized
        int expectedIngredients = FireBoard.BROTH_TARGET + FireBoard.CHILI_TARGET + FireBoard.MEAT_TARGET;
        assertEquals(expectedIngredients, fireBoard.ingredients.size(), "Incorrect number of ingredients initialized");
    }

    @Test
    void testGetRequiredIngredients() {
        List<String> requiredIngredients = fireBoard.getRequiredIngredients();
        assertNotNull(requiredIngredients, "Required ingredients list should not be null");
        assertEquals(3, requiredIngredients.size(), "There should be 3 required ingredients");
        assertTrue(requiredIngredients.contains("broth"), "Required ingredients should contain 'broth'");
        assertTrue(requiredIngredients.contains("chili"), "Required ingredients should contain 'chili'");
        assertTrue(requiredIngredients.contains("meat"), "Required ingredients should contain 'meat'");
    }

    @Test
    void testInitializeEnemies() {
        fireBoard.initializeEnemies();
        // Check that the correct number of enemies has been initialized
        int expectedStaticEnemies = 4;
        int expectedMovingEnemies = 1;
        assertEquals(expectedStaticEnemies, fireBoard.staticEnemies.size(), "Incorrect number of static enemies initialized");
        assertEquals(expectedMovingEnemies, fireBoard.movingEnemies.size(), "Incorrect number of moving enemies initialized");
    }

    @Test
    void testSpawnBonusIngredient() {
        // Mock the SoundManager to avoid playing actual sounds
        fireBoard.soundManager = mock(SoundManager.class);

        fireBoard.isGameOver = false;
        fireBoard.isGameWon = false;
        fireBoard.bonusCollected = false;

        fireBoard.spawnBonusIngredient();

        // Check that a bonus ingredient has been added
        boolean bonusIngredientExists = fireBoard.ingredients.stream()
                .anyMatch(ingredient -> ingredient instanceof BonusIngredient);

        assertTrue(bonusIngredientExists, "Bonus ingredient should have been spawned");

        // Verify that the sound was played
        verify(fireBoard.soundManager, times(1)).playSound("bonus_spawned");
    }

    @Test
    void testHandleBonusCollection() {
        fireBoard.bonusCollected = false;
        BonusIngredient bonusIngredient = new BonusIngredient(new Coordinate(1, 1), 20, "bonusIngredient", 5000, fireBoard);

        fireBoard.handleBonusCollection(bonusIngredient);

        assertTrue(fireBoard.bonusCollected, "Bonus should be marked as collected");
        assertTrue(fireBoard.hasBonusIceTea(), "Bonus ice tea should be collected");
        assertEquals(bonusIngredient.getValue(), fireBoard.playerScore, "Player score should increase by bonus value");
    }

    @Test
    void testInitializeBarriers() {
        fireBoard.initializeBarriers();
        int expectedBarriers = 30;
        assertEquals(expectedBarriers, fireBoard.barriers.size(), "Incorrect number of barriers initialized");
    }

    @Test
    void testCheckWinCondition_NotWon() {
        fireBoard.collectedIngredients.clear();
        fireBoard.checkWinCondition();
        assertFalse(fireBoard.isGameWon, "Game should not be won yet");
    }

    @Test
    void testCheckWinCondition_Won() {
        // Collect required ingredients
        for (int i = 0; i < FireBoard.BROTH_TARGET; i++) {
            fireBoard.collectedIngredients.add("broth");
        }
        for (int i = 0; i < FireBoard.CHILI_TARGET; i++) {
            fireBoard.collectedIngredients.add("chili");
        }
        for (int i = 0; i < FireBoard.MEAT_TARGET; i++) {
            fireBoard.collectedIngredients.add("meat");
        }

        // Mock timers
        fireBoard.enemyTimer = mock(Timer.class);
        fireBoard.bonusSpawnTimer = mock(Timer.class);

        fireBoard.checkWinCondition();

        assertTrue(fireBoard.isGameWon, "Game should be won");
        verify(fireBoard.enemyTimer).stop();
        verify(fireBoard.bonusSpawnTimer).stop();
    }

    @Test
    void testIsPlayerPosition() {
        fireBoard.playerX = 5;
        fireBoard.playerY = 5;
        assertTrue(fireBoard.isPlayerPosition(5, 5), "Should return true for player's position");
        assertFalse(fireBoard.isPlayerPosition(4, 5), "Should return false for other positions");
    }

    @Test
    void testCollectIngredient() {
        Ingredient ingredient = new RegularIngredient(new Coordinate(1, 1), 5, "chili", fireBoard);
        fireBoard.grid[1][1] = ingredient;
        fireBoard.playerScore = 0;

        fireBoard.collectIngredient(ingredient);

        assertEquals(5, fireBoard.playerScore, "Player score should increase by ingredient value");
        assertFalse(fireBoard.ingredients.contains(ingredient), "Ingredient should be removed from the list");
        assertNull(fireBoard.grid[1][1], "Ingredient should be removed from the grid");
    }

    @Test
    void testGetTargetForIngredient() {
        assertEquals(FireBoard.BROTH_TARGET, fireBoard.getTargetForIngredient("broth"), "Incorrect target for 'broth'");
        assertEquals(FireBoard.CHILI_TARGET, fireBoard.getTargetForIngredient("chili"), "Incorrect target for 'chili'");
        assertEquals(FireBoard.MEAT_TARGET, fireBoard.getTargetForIngredient("meat"), "Incorrect target for 'meat'");
        assertEquals(0, fireBoard.getTargetForIngredient("unknown"), "Target for unknown ingredient should be 0");
    }

    @Test
    void testGetters() {
        // Assuming the collected counts are tracked elsewhere; here we just test the getters
        assertEquals(0, fireBoard.getChiliCollected(), "Chili collected should be 0 initially");
        assertEquals(0, fireBoard.getBrothCollected(), "Broth collected should be 0 initially");
        assertEquals(0, fireBoard.getMeatCollected(), "Meat collected should be 0 initially");
        assertFalse(fireBoard.hasBonusIceTea(), "Bonus ice tea should not be collected initially");
    }

    @Test
    void testDrawEntity() {
        // Since Graphics is an abstract class, we'll use a mock
        Graphics g = mock(Graphics.class);
        Entity entity = mock(Entity.class);
        fireBoard.images = mock(Map.class);
        Image image = mock(Image.class);

        when(fireBoard.images.get(anyString())).thenReturn(image);

        // Test with different entities
        fireBoard.drawEntity(g, new Wall(new Coordinate(0, 0)), 0, 0);
        verify(fireBoard.images).get("wall");

        fireBoard.drawEntity(g, new Barrier(new Coordinate(0, 0)), 0, 0);
        verify(fireBoard.images).get("barrier");

        fireBoard.drawEntity(g, new StaticEnemy(new Coordinate(0, 0), 10), 0, 0);
        verify(fireBoard.images).get("staticEnemy");

        fireBoard.drawEntity(g, new MovingEnemy(new Coordinate(0, 0)), 0, 0);
        verify(fireBoard.images).get("movingEnemy");

        RegularIngredient ingredient = new RegularIngredient(new Coordinate(0, 0), 5, "chili", fireBoard);
        fireBoard.drawEntity(g, ingredient, 0, 0);
        verify(fireBoard.images).get("regularIngredient_chili");

        BonusIngredient bonusIngredient = new BonusIngredient(new Coordinate(0, 0), 20, "bonusIngredient", 5000, fireBoard);
        fireBoard.drawEntity(g, bonusIngredient, 0, 0);
        verify(fireBoard.images).get("bonusIngredient");

        // Verify that the image was drawn
        verify(g, atLeastOnce()).drawImage(eq(image), anyInt(), anyInt(), anyInt(), anyInt(), eq(fireBoard));
    }
}

