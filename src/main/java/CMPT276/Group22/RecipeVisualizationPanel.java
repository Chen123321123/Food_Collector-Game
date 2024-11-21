package CMPT276.Group22;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

/**
 * Displays a visual representation of a recipe within the game.
 * Used for showing the ingredients and steps needed to complete a recipe.
 */
public class RecipeVisualizationPanel extends JPanel {
    private Image baseImage;
    private Map<String, Image> overlayImages;
    private Map<String, Boolean> ingredientCompleted;
    private Image bonusImage;
    private boolean bonusCollected;
    private int imageWidth;
    private int imageHeight;
    private Board currentBoard;
    private final int VERTICAL_GAP = 0; // Reduced offset
    private final int BONUS_OFFSET = 15;
    private final int MAX_PANEL_WIDTH = 180;

    public RecipeVisualizationPanel(Board board) {
        this.currentBoard = board;
        this.overlayImages = new HashMap<>();
        this.ingredientCompleted = new HashMap<>();
        this.bonusCollected = false;
        
        setBackground(new Color(240, 240, 240));
        loadImages();
        
        // Set fixed size for the panel, now accounting for bonus image height
        setPreferredSize(new Dimension(MAX_PANEL_WIDTH, imageHeight * 2 + VERTICAL_GAP));
        setMaximumSize(new Dimension(MAX_PANEL_WIDTH, imageHeight * 2 + VERTICAL_GAP));
    }

    private void loadImages() {
        String recipePath = "";
        if (currentBoard instanceof FireBoard) {
            recipePath = "/assets/recipes/fire/";
            loadRecipeImages(recipePath, "hotpot",
                    Arrays.asList("broth", "chili", "meat"), "ice_tea");
        } else if (currentBoard instanceof IceBoard) {
            recipePath = "/assets/recipes/ice/";
            loadRecipeImages(recipePath, "bananasplit",
                    Arrays.asList("banana", "icecream", "cherry", "whipcream"), "milkshake");
        } else if (currentBoard instanceof EarthBoard) {
            recipePath = "/assets/recipes/earth/";
            loadRecipeImages(recipePath, "sambar",
                    Arrays.asList("dal", "potato", "carrot", "onion", "drumstick"), "rice");
        }
    }

    private void loadRecipeImages(String path, String recipeName, List<String> ingredients, String bonusName) {
        try {
            // Load and scale base image
            baseImage = loadImage(path + recipeName + "_base.png");
            if (baseImage != null) {
                // Calculate scaling to fit within MAX_PANEL_WIDTH
                double scale = (double)(MAX_PANEL_WIDTH - BONUS_OFFSET) / baseImage.getWidth(null);
                imageWidth = (int)(baseImage.getWidth(null) * scale);
                imageHeight = (int)(baseImage.getHeight(null) * scale);
                
                // Rescale base image
                baseImage = baseImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
                
                // Load and scale overlays
                for (String ingredient : ingredients) {
                    Image overlay = loadImage(path + recipeName + "_" + ingredient + ".png");
                    if (overlay != null) {
                        overlay = overlay.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
                        overlayImages.put(ingredient, overlay);
                        ingredientCompleted.put(ingredient, false);
                    }
                }

                // Load and scale bonus image
                bonusImage = loadImage(path + bonusName + ".png");
                if (bonusImage != null) {
                    bonusImage = bonusImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading recipe images: " + e.getMessage());
        }
    }

    private Image loadImage(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is != null) {
                BufferedImage img = ImageIO.read(is);
                // Scale image to fit panel width
                if (imageWidth > 0 && imageHeight > 0) {
                    return img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
                }
                return img;
            }
        } catch (IOException e) {
            System.err.println("Failed to load image: " + path);
        }
        return null;
    }

    // Make sure update methods also trigger revalidate
    public void updateIngredientState(String ingredient, boolean completed) {
        // Debug print to verify method is being called
        // System.out.println("Updating " + ingredient + " state to: " + completed);
        
        ingredientCompleted.put(ingredient.toLowerCase(), completed);
        revalidate();
        repaint();
    }

    public void updateBonusState(boolean collected) {
        // System.out.println("Updating bonus state to: " + collected); // Debug print
        this.bonusCollected = collected;
        // if (bonusImage != null) {
        //     System.out.println("Bonus image is loaded"); // Debug print
        // } else {
        //     System.out.println("Bonus image is null"); // Debug print
        // }
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (baseImage != null) {
            // Draw main recipe centered
            int x = (getWidth() - imageWidth) / 2;
            g.drawImage(baseImage, x, -20, imageWidth + 10, imageHeight + 10, this);
            
            // Draw ingredient overlays
            for (Map.Entry<String, Boolean> entry : ingredientCompleted.entrySet()) {
                if (entry.getValue()) {
                    Image overlay = overlayImages.get(entry.getKey());
                    if (overlay != null) {
                        g.drawImage(overlay, x, -20, imageWidth + 10, imageHeight + 10, this);
                    }
                }
            }
        }
        
        // Draw bonus image below main recipe if collected
        if (bonusCollected && bonusImage != null) {
            int x = (getWidth() - imageWidth) / 2;
            g.drawImage(bonusImage, x, -20 + imageHeight + 5, imageWidth + 5, imageHeight, this);
        }
    }

    
}
