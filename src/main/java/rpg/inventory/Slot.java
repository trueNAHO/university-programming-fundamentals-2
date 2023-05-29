package rpg.inventory;

import javafx.scene.image.Image;
import rpg.blocks.Block;

/**
 * Represents a slot block.
 */

public class Slot extends Block {

  /**
   * Constructs a Slot object with the specified coordinates.
   * @param x The x-coordinate of the slot.
   * @param y The y-coordinate of the slot.
   */
  
  public Slot(double x, double y) {
    super(x, y, 53, 53, new Image("sprites/slot/slot_stage_1.png"));
  }

  /**
   * Selects the slot by changing its image to the selected stage.
   */
  
  public void select() {
    changeImage(new Image("sprites/slot/slot_stage_2.png"));
  }

  /**
   * Unselects the slot by changing its image to the unselected stage.
   */

  public void unselect() {
    changeImage(new Image("sprites/slot/slot_stage_1.png"));
  }
}
