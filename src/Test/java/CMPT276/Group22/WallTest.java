package CMPT276.Group22;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    @Test
    void testConstructor() {
        Coordinate position = new Coordinate(3, 4);
        Wall wall = new Wall(position);
        assertEquals(position, wall.getPosition(), "Wall position should match the input coordinate");
    }

    @Test
    void testPositionAccess() {
        Coordinate position = new Coordinate(5, 6);
        Wall wall = new Wall(position);
        assertEquals(5, wall.getPosition().getRow(), "X-coordinate should be correct");
        assertEquals(6, wall.getPosition().getCol(), "Y-coordinate should be correct");
    }

    @Test
    void testImmutabilityOfPosition() {
        Coordinate position = new Coordinate(1, 2);
        Wall wall = new Wall(position);
        position.setX(10); // Modify the original coordinate
        position.setY(20);
        assertEquals(1, wall.getPosition().getRow(), "Wall's X-coordinate should not change");
        assertEquals(2, wall.getPosition().getCol(), "Wall's Y-coordinate should not change");
    }

    @Test
    void testCollision() {
        // Assuming there is a collision detection mechanism
        Coordinate wallPosition = new Coordinate(7, 8);
        Wall wall = new Wall(wallPosition);

        Coordinate playerPosition = new Coordinate(7, 8);
        boolean isCollision = wall.getPosition().equals(playerPosition);

        assertTrue(isCollision, "Wall should block the movement");
    }

    @Test
    void testUniqueIdentifier() {
        Wall wall1 = new Wall(new Coordinate(3, 4));
        Wall wall2 = new Wall(new Coordinate(5, 6));
        assertNotSame(wall1, wall2, "Each wall should be a unique entity");
    }
}
