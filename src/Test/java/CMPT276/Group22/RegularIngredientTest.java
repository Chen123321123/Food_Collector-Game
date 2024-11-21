package CMPT276.Group22;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegularIngredientTest {

    @Test
    void testConstructor() {
        Coordinate position = new Coordinate(3, 4);
        int value = 10;
        String name = "Test Ingredient";
        Board board = mock(Board.class);

        RegularIngredient ingredient = new RegularIngredient(position, value, name, board);

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

        RegularIngredient ingredient = new RegularIngredient(position, value, name, board);

        assertEquals(value, ingredient.getValue(), "getValue should return the correct value");
        assertEquals(name, ingredient.getName(), "getName should return the correct name");
    }

    @Test
    void testOnCollectedBehavior() {
        Coordinate position = new Coordinate(1, 1);
        int value = 30;
        String name = "Collectible Ingredient";
        Board board = mock(Board.class);

        RegularIngredient ingredient = new RegularIngredient(position, value, name, board);

        ingredient.onCollected(board);

        verify(board, times(1)).increasePlayerScore(value);
        verify(board, times(1)).addCollectedIngredient(name);
    }
}

