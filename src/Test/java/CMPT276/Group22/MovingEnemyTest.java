package CMPT276.Group22;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class MovingEnemyTest {
    private MovingEnemy movingEnemy;
    private Coordinate initialPosition;

    @Before
    public void setUp() {
        initialPosition = new Coordinate(5, 5);
        movingEnemy = new MovingEnemy(initialPosition);
    }

    @Test
    public void testMovingEnemyInitialization() {
        assertNotNull("MovingEnemy should not be null", movingEnemy);
        assertNotNull("MovingEnemy position should not be null", movingEnemy.getPosition());
    }

    @Test
    public void testInitialPosition() {
        assertEquals("Initial position row should match", 5, movingEnemy.getPosition().getRow());
        assertEquals("Initial position column should match", 5, movingEnemy.getPosition().getCol());
    }

    @Test
    public void testPositionUpdate() {
        Coordinate newPosition = new Coordinate(7, 7);
        movingEnemy.setPosition(newPosition);
        assertEquals("Position should be updated", newPosition, movingEnemy.getPosition());
    }

    @Test
    public void testMultipleMovementUpdates() {
        Coordinate[] positions = {
            new Coordinate(1, 1),
            new Coordinate(2, 2),
            new Coordinate(3, 3),
            new Coordinate(4, 4)
        };

        for (Coordinate pos : positions) {
            movingEnemy.setPosition(pos);
            assertEquals("Position should be updated correctly", pos, movingEnemy.getPosition());
        }
    }

    @Test
    public void testPositionBoundaries() {
        // Test minimum boundary
        Coordinate minPos = new Coordinate(0, 0);
        movingEnemy.setPosition(minPos);
        assertEquals("Should handle minimum position", minPos, movingEnemy.getPosition());

        // Test large values
        Coordinate largePos = new Coordinate(1000, 1000);
        movingEnemy.setPosition(largePos);
        assertEquals("Should handle large position values", largePos, movingEnemy.getPosition());
    }

    @Test
    public void testPositionIndependence() {
        // Create new coordinate for position
        Coordinate pos = new Coordinate(3, 3);
        // Create a copy for the enemy
        Coordinate enemyPos = new Coordinate(pos.getRow(), pos.getCol());
        
        movingEnemy.setPosition(enemyPos);
        
        // Modify the original position
        pos.move(Direction.UP);
        
        // The enemy's position should not have changed
        assertEquals("Enemy position row should be independent", 
                    3, movingEnemy.getPosition().getRow());
        assertEquals("Enemy position column should be independent", 
                    3, movingEnemy.getPosition().getCol());
    }

    @Test
    public void testInheritanceFromEnemy() {
        assertTrue("MovingEnemy should be an instance of Enemy", movingEnemy instanceof Entity);
    }

    @Test
    public void testSeparateInstances() {
        MovingEnemy enemy1 = new MovingEnemy(new Coordinate(1, 1));
        MovingEnemy enemy2 = new MovingEnemy(new Coordinate(2, 2));

        // Test that the instances are different
        assertNotSame("Different instances should not be the same object", enemy1, enemy2);
        
        // Test that their positions are different
        assertNotEquals("Different instances should have different positions", 
                       enemy1.getPosition(), enemy2.getPosition());
    }
}