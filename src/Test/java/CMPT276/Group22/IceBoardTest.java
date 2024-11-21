package CMPT276.Group22;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IceBoardTest {
    private IceBoard iceBoard;

    @BeforeEach
    void setUp() {
        iceBoard = new IceBoard();

        // Mock the images map to prevent actual image loading
        iceBoard.images = Mockito.mock(Map.class);

        // Initialize necessary fields
        iceBoard.grid = new Entity[IceBoard.GRID_HEIGHT][IceBoard.GRID_WIDTH];
        iceBoard.ingredients = new ArrayList<>();
        iceBoard.staticEnemies = new ArrayList<>();
        iceBoard.movingEnemies = new ArrayList<>();
        iceBoard.barriers = new ArrayList<>();
        iceBoard.collectedIngredients = new ArrayList<>();
        iceBoard.playerX = 0;
        iceBoard.playerY = 0;

        // Mock SoundManager to avoid playing actual sounds
        SoundManager soundManagerMock = mock(SoundManager.class);
        SoundManager.setInstance(soundManagerMock);
    }

    @Test
    void testLoadBoardSpecificImages() {
        iceBoard.loadBoardSpecificImages();
        // Verify that images are loaded (assuming loadImage adds entries to the images map)
        verify(iceBoard.images, atLeastOnce()).put(anyString(), any(Image.class));
    }

    @Test
    void testInitializeIngredients() {
        iceBoard.initializeIngredients();
        int expectedIngredients = IceBoard.BANANA_TARGET + IceBoard.ICE_CREAM_TARGET +
                                  IceBoard.CHERRY_TARGET + IceBoard.WHIP_CREAM_TARGET;
        assertEquals(expectedIngredients, iceBoard.ingredients.size(), "Incorrect number of ingredients initialized");
    }

    @Test
    void testGetRequiredIngredients() {
        List<String> requiredIngredients = iceBoard.getRequiredIngredients();
        assertNotNull(requiredIngredients, "Required ingredients list should not be null");
        assertEquals(4, requiredIngredients.size(), "There should be 4 required ingredients");
        assertTrue(requiredIngredients.contains("icecream"), "Required ingredients should contain 'icecream'");
        assertTrue(requiredIngredients.contains("whipcream"), "Required ingredients should contain 'whipcream'");
        assertTrue(requiredIngredients.contains("banana"), "Required ingredients should contain 'banana'");
        assertTrue(requiredIngredients.contains("cherry"), "Required ingredients should contain 'cherry'");
    }

    @Test
    void testInitializeEnemies() {
        iceBoard.initializeEnemies();
        int expectedStaticEnemies = 4;
        int expectedMovingEnemies = 2;
        assertEquals(expectedStaticEnemies, iceBoard.staticEnemies.size(), "Incorrect number of static enemies initialized");
        assertEquals(expectedMovingEnemies, iceBoard.movingEnemies.size(), "Incorrect number of moving enemies initialized");
    }

    @Test
    void testSpawnBonusIngredient() {
        iceBoard.isGameOver = false;
        iceBoard.isGameWon = false;
        iceBoard.bonusCollected = false;
        iceBoard.bonusSpawnTimer = mock(Timer.class);

        iceBoard.spawnBonusIngredient();

        boolean bonusIngredientExists = iceBoard.ingredients.stream()
                .anyMatch(ingredient -> ingredient instanceof BonusIngredient && ingredient.getName().equals("milkshake"));

        assertTrue(bonusIngredientExists, "Bonus ingredient should have been spawned");

        // Verify that the sound was played
        SoundManager soundManager = SoundManager.getInstance();
        verify(soundManager, times(1)).playSound("bonus_spawned");
    }

    @Test
    void testHandleBonusCollection() {
        iceBoard.bonusCollected = false;
        BonusIngredient bonusIngredient = new BonusIngredient(new Coordinate(1, 1), 20, "milkshake", 5000, iceBoard);

        iceBoard.handleBonusCollection(bonusIngredient);

        assertTrue(iceBoard.bonusCollected, "Bonus should be marked as collected");
        assertTrue(iceBoard.hasBonusMilkshake(), "Bonus milkshake should be collected");
        assertEquals(bonusIngredient.getValue(), iceBoard.playerScore, "Player score should increase by bonus value");

        // Verify that the bonusSpawnTimer is stopped
        if (iceBoard.bonusSpawnTimer != null) {
            verify(iceBoard.bonusSpawnTimer, times(1)).stop();
        }
    }

    @Test
    void testInitializeBarriers() {
        iceBoard.initializeBarriers();
        int expectedBarriers = 12;
        assertEquals(expectedBarriers, iceBoard.barriers.size(), "Incorrect number of barriers initialized");
    }

    @Test
    void testCheckWinCondition_NotWon() {
        iceBoard.collectedIngredients.clear();
        iceBoard.checkWinCondition();
        assertFalse(iceBoard.isGameWon, "Game should not be won yet");
    }

    @Test
    void testCheckWinCondition_Won() {
        // Collect required ingredients
        for (int i = 0; i < IceBoard.BANANA_TARGET; i++) {
            iceBoard.collectedIngredients.add("banana");
        }
        for (int i = 0; i < IceBoard.ICE_CREAM_TARGET; i++) {
            iceBoard.collectedIngredients.add("icecream");
        }
        for (int i = 0; i < IceBoard.CHERRY_TARGET; i++) {
            iceBoard.collectedIngredients.add("cherry");
        }
        for (int i = 0; i < IceBoard.WHIP_CREAM_TARGET; i++) {
            iceBoard.collectedIngredients.add("whipcream");
        }

        iceBoard.enemyTimer = mock(Timer.class);
        iceBoard.bonusSpawnTimer = mock(Timer.class);
        SoundManager soundManager = SoundManager.getInstance();

        iceBoard.checkWinCondition();

        assertTrue(iceBoard.isGameWon, "Game should be won");
        verify(iceBoard.enemyTimer, times(1)).stop();
        verify(iceBoard.bonusSpawnTimer, times(1)).stop();
        verify(soundManager, times(1)).playSound("win");
    }

    @Test
    void testIsPlayerPosition() {
        iceBoard.playerX = 5;
        iceBoard.playerY = 5;
        assertTrue(iceBoard.isPlayerPosition(5, 5), "Should return true for player's position");
        assertFalse(iceBoard.isPlayerPosition(4, 5), "Should return false for other positions");
    }

    @Test
    void testCollectIngredient() {
        iceBoard.playerScore = 0;

        Ingredient banana = new RegularIngredient(new Coordinate(1, 1), 5, "banana", iceBoard);
        iceBoard.collectIngredient(banana);
        assertEquals(1, iceBoard.getBananaCollected(), "Banana collected count should be 1");

        Ingredient iceCream = new RegularIngredient(new Coordinate(1, 1), 5, "iceCream", iceBoard);
        iceBoard.collectIngredient(iceCream);
        assertEquals(1, iceBoard.getIceCreamCollected(), "Ice cream collected count should be 1");

        Ingredient cherry = new RegularIngredient(new Coordinate(1, 1), 5, "cherry", iceBoard);
        iceBoard.collectIngredient(cherry);
        assertEquals(1, iceBoard.getCherryCollected(), "Cherry collected count should be 1");

        Ingredient whipCream = new RegularIngredient(new Coordinate(1, 1), 5, "whipCream", iceBoard);
        iceBoard.collectIngredient(whipCream);
        assertEquals(1, iceBoard.getWhipCreamCollected(), "Whip cream collected count should be 1");

        Ingredient milkshake = new BonusIngredient(new Coordinate(1, 1), 20, "milkshake", 5000, iceBoard);
        iceBoard.collectIngredient(milkshake);
        assertTrue(iceBoard.hasBonusMilkshake(), "Bonus milkshake should be collected");
    }

    @Test
    void testDrawEntity() {
        // Since Graphics is abstract, we'll use a mock
        Graphics g = mock(Graphics.class);
        iceBoard.images = mock(Map.class);
        Image imageMock = mock(Image.class);

        when(iceBoard.images.get(anyString())).thenReturn(imageMock);

        // Test with different entities
        iceBoard.drawEntity(g, new Wall(new Coordinate(0, 0)), 0, 0);
        verify(iceBoard.images).get("wall");

        iceBoard.drawEntity(g, new Barrier(new Coordinate(0, 0)), 0, 0);
        verify(iceBoard.images).get("barrier");

        iceBoard.drawEntity(g, new StaticEnemy(new Coordinate(0, 0), 15), 0, 0);
        verify(iceBoard.images).get("staticEnemy");

        iceBoard.drawEntity(g, new MovingEnemy(new Coordinate(0, 0)), 0, 0);
        verify(iceBoard.images).get("movingEnemy");

        RegularIngredient iceCream = new RegularIngredient(new Coordinate(0, 0), 5, "icecream", iceBoard);
        iceBoard.drawEntity(g, iceCream, 0, 0);
        verify(iceBoard.images).get("regularIngredient_icecream");

        BonusIngredient milkshake = new BonusIngredient(new Coordinate(0, 0), 20, "milkshake", 5000, iceBoard);
        iceBoard.drawEntity(g, milkshake, 0, 0);
        verify(iceBoard.images).get("bonusIngredient");

        // Verify that the image was drawn
        verify(g, atLeastOnce()).drawImage(eq(imageMock), anyInt(), anyInt(), anyInt(), anyInt(), eq(iceBoard));
    }

    @Test
    void testGetTargetForIngredient() {
        assertEquals(IceBoard.BANANA_TARGET, iceBoard.getTargetForIngredient("banana"), "Incorrect target for 'banana'");
        assertEquals(IceBoard.ICE_CREAM_TARGET, iceBoard.getTargetForIngredient("icecream"), "Incorrect target for 'icecream'");
        assertEquals(IceBoard.CHERRY_TARGET, iceBoard.getTargetForIngredient("cherry"), "Incorrect target for 'cherry'");
        assertEquals(IceBoard.WHIP_CREAM_TARGET, iceBoard.getTargetForIngredient("whipcream"), "Incorrect target for 'whipcream'");
        assertEquals(0, iceBoard.getTargetForIngredient("unknown"), "Target for unknown ingredient should be 0");
    }

    @Test
    void testGetters() {
        assertEquals(0, iceBoard.getBananaCollected(), "Banana collected should be 0 initially");
        assertEquals(0, iceBoard.getIceCreamCollected(), "Ice cream collected should be 0 initially");
        assertEquals(0, iceBoard.getCherryCollected(), "Cherry collected should be 0 initially");
        assertEquals(0, iceBoard.getWhipCreamCollected(), "Whip cream collected should be 0 initially");
        assertFalse(iceBoard.hasBonusMilkshake(), "Bonus milkshake should not be collected initially");
    }
}

