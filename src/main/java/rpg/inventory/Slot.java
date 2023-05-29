package rpg.inventory;

import javafx.scene.image.Image;
import rpg.blocks.Block;

public class Slot extends Block {

  public Slot(double x, double y) {
    super(x, y, 53, 53, new Image("sprites/slot/slot_stage_1.png"));
  }

  public void select() {
    changeImage(new Image("sprites/slot/slot_stage_2.png"));
  }

  public void unselect() {
    changeImage(new Image("sprites/slot/slot_stage_1.png"));
  }
}
