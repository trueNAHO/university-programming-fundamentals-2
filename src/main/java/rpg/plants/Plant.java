package rpg.plants;

import javafx.scene.image.Image;
import rpg.blocks.Block;

/**
 * Represents the Plant block.
 */

public class Plant extends Block {
  private static final String MAX_STAGE_VALUE = "final";
  private static final int MAX_STAGES = 3;
  private static final int START_STAGE = 1;

  public String type;
  private int stage = START_STAGE;
  private Image image;
  private double width;
  private double height;
  private boolean grown;

  /**
   * Constructs a Plant object with the specified coordinates, width, height, type, and first image.
   * @param x The x-coordinate of the plant.
   * @param y The y-coordinate of the plant.
   * @param width The width of the plant.
   * @param height The height of the plant.
   * @param type The type of the plant.
   * @param firstImage The first image of the plant.
  */
  
  public Plant(double x, double y, double width, double height, String type, Image firstImage) {
    super(x, y, width, height, firstImage);
    this.type = type;
    this.width = width;
    this.height = height;
    this.grown = false;
    grow();
  }

  /**
   * Increases the stage of the plant, therefore growing it.
   */
  
  public void grow() {
    this.setStage(this.stage + 1);
  }

  /*
   * Checks if the plant is fullygrown, so if it has arrived at the last stage.
   * @return True if the plant has fully grown, false otherwise.
   */
  
  public boolean fullyGrown() {
    return this.grown;
  }

  /**
   * Resets the plant to the start/initial stage.
   */
  
  public void reset() {
    this.setStage(START_STAGE);
    this.grown = false;
  }

  /**
   * Sets the plants stage.
   * @param newStage The new stage to be set.
   */
  
  public void setStage(int newStage) {
    if (this.stage > MAX_STAGES && newStage > MAX_STAGES) {
      return;
    }

    this.stage = newStage;
    if (this.stage > MAX_STAGES) {
      this.grown = true;
    }
    this.SetImage();
  }

  /**
   * Sets the plants type.
   * @param newType The new type to be set.
   */
  
  public void setType(String newType) {
    if (newType.equals(this.type)) return;

    this.stage = START_STAGE;
    this.grown = false;
    this.type = newType;
    this.SetImage();
  }

  /**
   * Sets the image of the plant based on it's stage and type.
   */
  
  public void SetImage() {
    String stageValue = this.stage <= MAX_STAGES ? String.valueOf(this.stage) : MAX_STAGE_VALUE;

    this.image =
        new Image(
            ("sprites/" + this.type + "/" + this.type + "_stage_" + stageValue + ".png"),
            this.width,
            this.height,
            false,
            false);

    changeImage(this.image);
  }
}
