package rpg.blocks;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/** Class which represents a block in the game. It extends the rectangle class of JavaFX */
public class Block extends Rectangle {
  public final Label textBox;

  private BlockState state;
  private static final String TEXT_BOX_TEXT = "Interactable";

  /**
    * Constructs a Block object with the specified position, dimensions, and color.
    *
    * @param x The x-coordinate of the blocks position.
    * @param y The y-coordinate of the blocks position.
    * @param width The width of the block.
    * @param height The height of the block.
    * @param color The color of the block.
  */

  public Block(double x, double y, double width, double height, Color color) {
    super(x, y, width, height);
    this.state = new BlockNullState();
    setFill(color);

    this.textBox = new Label();
    this.textBox.relocate(x, y);
    this.textBox.setStyle("-fx-background-color: white;");
    this.textBox.setText(TEXT_BOX_TEXT);
    this.textBox.toFront();
    this.textBox.setVisible(false);
  }

  /**
  * Constructs a Block object with the specified position, dimensions, and color.
  *
  * @param x The x-coordinate of the blocks position.
  * @param y The y-coordinate of the blocks position.
  * @param width The width of the block.
  * @param height The height of the block.
  * @param image The image of the block.
*/
  public Block(double x, double y, double width, double height, Image image) {
    super(x, y, width, height);
    this.state = new BlockNullState();
    setFill(new ImagePattern(image));

    this.textBox = new Label();
    this.textBox.relocate(x, y);
    this.textBox.setStyle("-fx-background-color: white;");
    this.textBox.setText(TEXT_BOX_TEXT);
    this.textBox.toFront();
    this.textBox.setVisible(false);
  }

  /**
   * Checks if the block is interactable
   * @return True if the block is interactable, false if not 
   */
  
  public boolean isInteractable() {
    return this.state instanceof BlockInteractableState;
  }

  /**
   * Sets the state for a block
   * Updates the tex box and performs necessary state transitions
   * @param newState The new state of the block
   */
  
  public void setState(BlockState newState) {
    this.state.exit(this.textBox);
    this.state = newState;
    this.state.enter(this.textBox);
  }

  /**
   * Sets the text for the text box of the block 
   * @param text The text to set
   */
  
  public void setText(String text) {
    this.textBox.setText(text);
  }

  /**
   * Change the image of the block
   * @param image The new image for the block
   */
  
  public void changeImage(Image image) {
    setFill(new ImagePattern(image));
  }

  /**
   * Updates the blocks state
   * @param deltatime The time elapsed since the last update
   */

  public void update(double deltaTime) {
    this.state.update();
  }
}
