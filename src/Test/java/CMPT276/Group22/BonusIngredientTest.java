package CMPT276.Group22;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BonusIngredientTest {

    @Test
    void testConstructor() {
        Coordinate position = new Coordinate(3, 4);
        int value = 10;
        String name = "Bonus Ingredient";
        int duration = 5000; // 5 seconds
        Board board = mock(Board.class);

        BonusIngredient ingredient = new BonusIngredient(position, value, name, duration, board);

        assertEquals(position, ingredient.getPosition(), "Position should match the input coordinate");
        assertEquals(value, ingredient.getValue(), "Value should match the input value");
        assertEquals(name, ingredient.getName(), "Name should match the input name");
        assertEquals(board, ingredient.board, "Board reference should match the input board");
    }

    @Test
    void testExpirationTimer() throws InterruptedException {
        Board board = mock(Board.class);
        BonusIngredient ingredient = new BonusIngredient(
                new Coordinate(5, 6), 20, "Expiring Bonus", 100, board); // 100ms duration

        Thread.sleep(200); // Wait for the timer to expire

        verify(board, times(1)).removeIngredient(ingredient);
    }

    // @Test
    // void testOnCollectedBehavior() {
    //     Board board = mock(Board.class);
    //     RecipeVisualizationPanel visPanel = mock(RecipeVisualizationPanel.class);
    //     GameBoard gameBoard = mock(GameBoard.class);
    //     when(board.gameBoard).thenReturn(gameBoard);
    //     when(gameBoard.getRecipeVisualizationPanel()).thenReturn(visPanel);

    //     BonusIngredient ingredient = new BonusIngredient(
    //             new Coordinate(1, 1), 30, "Collected Bonus", 5000, board);

    //     ingredient.onCollected(board);

    //     verify(board, times(1)).collectIngredient(ingredient);
    //     assertTrue(board.bonusCollected, "bonusCollected should be set to true");
    //     verify(visPanel, times(1)).updateBonusState(true);
    // }

    @Test
    void testTimerNonRepetition() throws InterruptedException {
        Board board = mock(Board.class);
        BonusIngredient ingredient = new BonusIngredient(
                new Coordinate(7, 8), 50, "Non-Repeating Timer", 100, board); // 100ms duration

        Thread.sleep(200); // Wait for the timer to expire

        verify(board, times(1)).removeIngredient(ingredient);

        // Simulate additional wait to confirm no further calls
        Thread.sleep(100);
        verify(board, times(1)).removeIngredient(ingredient); // No additional calls
    }
}
