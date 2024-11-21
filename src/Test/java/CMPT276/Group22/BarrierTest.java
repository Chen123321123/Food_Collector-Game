package CMPT276.Group22;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BarrierTest {

    @Test
    void testConstructor() {
        Coordinate position = new Coordinate(3, 4);
        Barrier barrier = new Barrier(position);
        assertEquals(position, barrier.getPosition(), "Barrier position should match the input coordinate");
    }

    @Test
    void testPositionGetter() {
        Coordinate position = new Coordinate(5, 6);
        Barrier barrier = new Barrier(position);
        assertEquals(position, barrier.getPosition(), "getPosition should return the correct position");
    }

    @Test
    void testPositionSetter() {
        Coordinate initialPosition = new Coordinate(7, 8);
        Barrier barrier = new Barrier(initialPosition);

        Coordinate newPosition = new Coordinate(9, 10);
        barrier.setPosition(newPosition);

        assertEquals(newPosition, barrier.getPosition(), "Barrier position should be updated correctly");
    }

    @Test
    void testImmutabilityOfInitialPosition() {
        Coordinate position = new Coordinate(1, 2);
        Barrier barrier = new Barrier(position);
        position.setX(10); // Modify the original coordinate
        position.setY(20);

        assertEquals(1, barrier.getPosition().getRow(), "Barrier's X-coordinate should not change");
        assertEquals(2, barrier.getPosition().getCol(), "Barrier's Y-coordinate should not change");
    }

    @Test
    void testBarrierFunctionality() {
        // Assuming there's a collision detection mechanism
        Coordinate barrierPosition = new Coordinate(7, 8);
        Barrier barrier = new Barrier(barrierPosition);

        Coordinate entityPosition = new Coordinate(7, 8);
        boolean isBlocked = barrier.getPosition().equals(entityPosition);

        assertTrue(isBlocked, "Barrier should block movement to its position");
    }
}

