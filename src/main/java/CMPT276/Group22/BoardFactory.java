package CMPT276.Group22;

/**
 * Factory class for creating game boards.
 * Responsible for initializing the grid and placing entities such as walls and barriers.
 */
public class BoardFactory {
    public static Board createBoard(String boardType) {
        if (boardType.equalsIgnoreCase("fire")) {
            return new FireBoard();
        } else if (boardType.equalsIgnoreCase("ice")) {
            return new IceBoard();
        } else if (boardType.equalsIgnoreCase("earth")) {
            return new EarthBoard();
        } else {
            return null;
        }
    }
}

