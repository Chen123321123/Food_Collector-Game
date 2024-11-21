package CMPT276.Group22;

/**
 * Represents a bonus ingredient in the game.
 * Bonus ingredients provide extra points to the player upon collection.
 */
public class BonusIngredient extends Ingredient {
    private int duration; // Duration in milliseconds
    private javax.swing.Timer expirationTimer;

    public BonusIngredient(Coordinate position, int value, String name, int duration, Board board) {
        super(position, value, name, board);
        this.duration = duration;
        startExpirationTimer();
    }
    

    private void startExpirationTimer() {
        expirationTimer = new javax.swing.Timer(duration, e -> {
            if (board != null) {
                board.removeIngredient(this);
            }
            expirationTimer.stop();
        });
        expirationTimer.setRepeats(false);
        expirationTimer.start();
    }
    

    @Override
    public void onCollected(Board board) {
        board.bonusCollected = true;
        board.collectIngredient(this);
        // Notify visualization panel
        if (board.gameBoard != null) {
            RecipeVisualizationPanel visPanel = board.gameBoard.getRecipeVisualizationPanel();
            if (visPanel != null) {
                visPanel.updateBonusState(true);
            }
        }
    }
}


