package CMPT276.Group22;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientTest {
	
	// Inner helper class for testing
    static class TestIngredient extends Ingredient {
        private boolean collected = false;

        public TestIngredient(Coordinate position, int value, String name, Board board) {
            super(position, value, name, board);
        }

        @Override
        public void onCollected(Board board) {
            this.collected = true;
        }

        public boolean isCollected() {
            return collected;
        }
    }


    @Test
    void testConstructor() {
        Coordinate position = new Coordinate(3, 4);
        int value = 10;
        String name = "Test Ingredient";
        Board board = mock(Board.class);

        Ingredient ingredient = new TestIngredient(position, value, name, board);

        assertEquals(position, ingredient.getPosition(), "Position should match the input coordinate");
        assertEquals(value, ingredient.getValue(), "Value should match the input value");
        assertEquals(name, ingredient.getName(), "Name should match the input name");
        assertEquals(board, ingredient.board, "Board reference should match the input board");
    }

    @Test
    void testGetterMethods() {
        Coordinate position = new Coordinate(5, 6);
        int value = 20;
        String name = "Sample Ingredient";
        Board board = mock(Board.class);

        Ingredient ingredient = new TestIngredient(position, value, name, board);

        assertEquals(value, ingredient.getValue(), "getValue should return the correct value");
        assertEquals(name, ingredient.getName(), "getName should return the correct name");
    }

    @Test
    void testPositionInheritance() {
        Coordinate position = new Coordinate(7, 8);
        int value = 15;
        String name = "Inherited Ingredient";
        Board board = mock(Board.class);

        Ingredient ingredient = new TestIngredient(position, value, name, board);

        assertEquals(position, ingredient.getPosition(), "getPosition should return the correct position");
    }

    @Test
    void testOnCollectedBehavior() {
        Coordinate position = new Coordinate(1, 1);
        int value = 30;
        String name = "Collectible Ingredient";
        Board board = mock(Board.class);

        TestIngredient ingredient = new TestIngredient(position, value, name, board);

        assertFalse(ingredient.isCollected(), "Ingredient should not be collected initially");
        
        ingredient.onCollected(board);
        assertTrue(ingredient.isCollected(), "Ingredient should be marked as collected after onCollected()");
    }
}
