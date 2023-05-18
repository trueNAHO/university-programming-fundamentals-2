package rpg.blocks;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
  private BlockState state;

  public Block(double x, double y, double width, double height, Color color) {
    super(x, y, width, height);
    this.state = new BlockNullState();
    setFill(color);
  }

  public boolean isInteractable() {
    return this.state instanceof BlockInteractableState;
  }

  public void setState(BlockState newState) {
    this.state.exit();
    this.state = newState;
    this.state.enter();
  }

  public void update(double deltaTime) {
    this.state.update();
  }
}
