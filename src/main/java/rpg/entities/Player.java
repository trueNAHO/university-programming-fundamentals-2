package rpg.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import rpg.entities.states.NullState;
import rpg.entities.states.PlayerState;

public class Player extends Rectangle {
  private PlayerState state;

  public Player(double x, double y, double width, double height, Color color) {
    super(x, y, width, height);
    this.state = new NullState();
    setFill(color);
  }

  public void moveDown() {}

  public void moveLeft() {}

  public void moveRight() {}

  public void moveUp() {}

  public void setState(PlayerState newState) {
    this.state.exit(this);
    this.state = newState;
    this.state.enter(this);
  }

  public void update(double deltaTime) {
    this.state.update(this, deltaTime);
  }
}
