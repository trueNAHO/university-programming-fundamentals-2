package rpg.blocks;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
  public Block(double x, double y, double width, double height, Color color) {
    super(x, y, width, height);
    setFill(color);
  }

  public void update(double deltaTime) {}
}
