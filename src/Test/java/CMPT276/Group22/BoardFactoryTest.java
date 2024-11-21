package CMPT276.Group22;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardFactoryTest {

    @Test
    void testFireBoardCreation() {
        Board board = BoardFactory.createBoard("fire");
        assertNotNull(board, "FireBoard should not be null");
        assertTrue(board instanceof FireBoard, "Expected instance of FireBoard");
    }

    @Test
    void testIceBoardCreation() {
        Board board = BoardFactory.createBoard("ice");
        assertNotNull(board, "IceBoard should not be null");
        assertTrue(board instanceof IceBoard, "Expected instance of IceBoard");
    }

    @Test
    void testEarthBoardCreation() {
        Board board = BoardFactory.createBoard("earth");
        assertNotNull(board, "EarthBoard should not be null");
        assertTrue(board instanceof EarthBoard, "Expected instance of EarthBoard");
    }

    @Test
    void testCaseInsensitiveInput() {
        Board board = BoardFactory.createBoard("FIRE");
        assertNotNull(board, "FireBoard should not be null");
        assertTrue(board instanceof FireBoard, "Expected instance of FireBoard");

        board = BoardFactory.createBoard("Ice");
        assertNotNull(board, "IceBoard should not be null");
        assertTrue(board instanceof IceBoard, "Expected instance of IceBoard");

        board = BoardFactory.createBoard("EaRtH");
        assertNotNull(board, "EarthBoard should not be null");
        assertTrue(board instanceof EarthBoard, "Expected instance of EarthBoard");
    }

    @Test
    void testInvalidInput() {
        Board board = BoardFactory.createBoard("water");
        assertNull(board, "Invalid board type should return null");
    }
}
