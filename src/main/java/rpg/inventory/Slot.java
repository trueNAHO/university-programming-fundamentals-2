package rpg.inventory;

import javafx.scene.image.Image;
import rpg.blocks.Block;

public class Slot extends Block {
  /*
  private Image image = new Image("sprites/slot/slot_stage_1.png");
  private double width = 53;
  private double height = 53;
  */

  public Slot(double x, double y) {
    super(x, y, 53, 53, new Image("sprites/slot/slot_stage_1.png"));
  }

  public void select() {
    changeImage(new Image("sprites/slot/slot_stage_1.png"));
  }

  public void unselect() {
    changeImage(new Image("sprites/slot/slot_stage_1.png"));
  }
}
