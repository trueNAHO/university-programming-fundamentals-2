package rpg.entities.player;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import rpg.entities.player.states.NullState;
import rpg.entities.player.states.PlayerState;

public class Player extends Rectangle {
  private static final double SPEED = 1;

  private PlayerState state;

  public Player(double x, double y, double width, double height, Color color) {
    super(x, y, width, height);
    this.state = new NullState();
    setFill(color);
  }

  public Player(double x, double y, double width, double height, Image image) {
    super(x, y, width, height);
    this.state = new NullState();
    setFill(new ImagePattern(image));
  }

  public void moveDown(double deltaTime) {
    setY(getY() + SPEED * deltaTime);
  }

  public void moveLeft(double deltaTime) {
    setX(getX() - SPEED * deltaTime);
  }

  public void moveRight(double deltaTime) {
    setX(getX() + SPEED * deltaTime);
  }

  public void moveUp(double deltaTime) {
    setY(getY() - SPEED * deltaTime);
  }

  public void setState(PlayerState newState) {
    this.state.exit(this);
    this.state = newState;
    this.state.enter(this);
  }

  public void update(double deltaTime) {
    this.state.update(this, deltaTime);
  }
}
