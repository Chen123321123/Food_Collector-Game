package CMPT276.Group22;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {

    private class TestEntity extends Entity {
        public TestEntity(Coordinate position) {
            super(position);
        }
    }


    @Test
    void testConstructor() {
        Coordinate position = new Coordinate(3, 4);
        Entity entity = new TestEntity(position);
        assertEquals(position, entity.getPosition(), "Entity position should match the input coordinate");
    }

    @Test
    void testPositionGetter() {
        Coordinate position = new Coordinate(5, 6);
        Entity entity = new TestEntity(position);
        assertEquals(position, entity.getPosition(), "getPosition should return the correct position");
    }

    @Test
    void testPositionSetter() {
        Coordinate initialPosition = new Coordinate(7, 8);
        Entity entity = new TestEntity(initialPosition);

        Coordinate newPosition = new Coordinate(9, 10);
        entity.setPosition(newPosition);

        assertEquals(newPosition, entity.getPosition(), "Entity position should be updated correctly");
    }

    @Test
    void testImmutabilityOfInitialPosition() {
        Coordinate position = new Coordinate(1, 2);
        Entity entity = new TestEntity(position);
        position.setX(10); // Modify the original coordinate
        position.setY(20);

        assertEquals(1, entity.getPosition().getRow(), "Entity's X-coordinate should not change");
        assertEquals(2, entity.getPosition().getCol(), "Entity's Y-coordinate should not change");
    }

    @Test
    void testInteractMethod() {
        class CustomEntity extends TestEntity {
            private boolean interacted = false;

            public CustomEntity(Coordinate position) {
                super(position);
            }

            @Override
            public void interact() {
                interacted = true;
            }

            public boolean hasInteracted() {
                return interacted;
            }
        }

        CustomEntity entity = new CustomEntity(new Coordinate(1, 1));
        assertFalse(entity.hasInteracted(), "Entity should not have interacted initially");
        
        entity.interact();
        assertTrue(entity.hasInteracted(), "Entity should set interacted flag when interact() is called");
    }
}
