package rpg.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
  public Player(double x, double y, double width, double height, Color color) {
    super(x, y, width, height);
    setFill(color);
  }

  public void update(double deltaTime) {}
}
