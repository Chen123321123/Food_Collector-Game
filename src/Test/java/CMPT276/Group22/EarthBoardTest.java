package CMPT276.Group22;

import org.junit.Test;
import static org.junit.Assert.*;

public class EarthBoardTest {
    // @Test
    // void testLoadBoardSpecificImages() {
    //     EarthBoard earthBoard = new EarthBoard();
    //     earthBoard.loadBoardSpecificImages();
    //     assertNotNull(earthBoard.getImage("background"));
    //     assertNotNull(earthBoard.getImage("wall"));
    //     assertNotNull(earthBoard.getImage("player"));
    // }

    // @Test
    // void testInitializeIngredients() {
    //     EarthBoard earthBoard = new EarthBoard();
    //     earthBoard.initializeIngredients();
    //     assertEquals(EarthBoard.DAL_TARGET, earthBoard.getDalCollected());
    //     assertEquals(EarthBoard.POTATO_TARGET, earthBoard.getPotatoCollected());
    //     assertEquals(EarthBoard.CARROT_TARGET, earthBoard.getCarrotCollected());
    //     assertEquals(EarthBoard.ONION_TARGET, earthBoard.getOnionCollected());
    // }

    // @Test
    // void testAddRegularIngredients() {
    //     EarthBoard earthBoard = new EarthBoard();
    //     earthBoard.addRegularIngredients("dal", 2, 5);
    //     assertTrue(earthBoard.getIngredients().stream().anyMatch(i -> i.getName().equals("dal")));
    // }

    // @Test
    // void testGetRequiredIngredients() {
    //     EarthBoard earthBoard = new EarthBoard();
    //     List<String> required = earthBoard.getRequiredIngredients();
    //     assertEquals(Arrays.asList("dal", "potato", "carrot", "onion"), required);
    // }

    // @Test
    // void testInitializeEnemies() {
    //     EarthBoard earthBoard = new EarthBoard();
    //     earthBoard.initializeEnemies();
    //     assertEquals(6, earthBoard.getStaticEnemies().size());
    //     assertEquals(3, earthBoard.getMovingEnemies().size());
    // }

    // @Test
    // void testSpawnBonusIngredient() {
    //     EarthBoard earthBoard = new EarthBoard();
    //     earthBoard.spawnBonusIngredient();
    //     assertTrue(earthBoard.getIngredients().stream().anyMatch(i -> i instanceof BonusIngredient));
    // }

    // @Test
    // void testHandleBonusCollection() {
    //     EarthBoard earthBoard = new EarthBoard();
    //     BonusIngredient bonus = new BonusIngredient(new Coordinate(1, 1), 20, "rice", 5000, earthBoard);
    //     earthBoard.handleBonusCollection(bonus);
    //     assertTrue(earthBoard.hasBonusRice());
    // }

    // @Test
    // void testInitializeBarriers() {
    //     EarthBoard earthBoard = new EarthBoard();
    //     earthBoard.initializeBarriers();
    //     assertEquals(18, earthBoard.getBarriers().size());
    // }

    // @Test
    // void testCheckWinCondition() {
    //     EarthBoard earthBoard = new EarthBoard();
    //     earthBoard.collectIngredient(new RegularIngredient(new Coordinate(0, 0), 5, "dal", earthBoard));
    //     earthBoard.collectIngredient(new RegularIngredient(new Coordinate(0, 1), 5, "potato", earthBoard));
    //     earthBoard.collectIngredient(new RegularIngredient(new Coordinate(0, 2), 5, "carrot", earthBoard));
    //     earthBoard.collectIngredient(new RegularIngredient(new Coordinate(0, 3), 5, "onion", earthBoard));
    //     earthBoard.checkWinCondition();
    //     assertTrue(earthBoard.isGameWon());
    // }

    // @Test
    // void testCollectIngredient() {
    //     EarthBoard earthBoard = new EarthBoard();
    //     RegularIngredient dal = new RegularIngredient(new Coordinate(0, 0), 5, "dal", earthBoard);
    //     earthBoard.collectIngredient(dal);
    //     assertEquals(1, earthBoard.getDalCollected());
    // }

    // @Test
    // void testDrawEntity() {
    //     EarthBoard earthBoard = new EarthBoard();
    //     Graphics g = mock(Graphics.class); // Using mockito to mock Graphics
    //     Entity wall = new Wall(new Coordinate(0, 0));
    //     earthBoard.drawEntity(g, wall, 0, 0);
    //     verify(g, atLeastOnce()).drawImage(any(), anyInt(), anyInt(), anyInt(), anyInt(), any());
    // }

    // @Test
    // void testGetTargetForIngredient() {
    //     EarthBoard earthBoard = new EarthBoard();
    //     assertEquals(2, earthBoard.getTargetForIngredient("dal"));
    //     assertEquals(3, earthBoard.getTargetForIngredient("potato"));
    // }

    // @Test
    // void testGetters() {
    //     EarthBoard earthBoard = new EarthBoard();
    //     assertEquals(0, earthBoard.getDalCollected());
    //     assertEquals(0, earthBoard.getPotatoCollected());
    // }    
        
}
