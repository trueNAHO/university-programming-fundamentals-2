package rpg.entities.player;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import rpg.entities.player.states.NullState;
import rpg.entities.player.states.PlayerState;

/**
 * Represents the player in the game.
 */

public class Player extends Rectangle {
  private static final double SPEED = 1;

  private PlayerState state;

  /**
   * Constructs a Player object with specified position, size, and color.
   * @param x The x-coordinate of the player's position.
   * @param y The y-coordinate of the player's position.
   * @param width The width of the player.
   * @param height The height of the player.
   * @param color The color of the player.
  */

  public Player(double x, double y, double width, double height, Color color) {
    super(x, y, width, height);
    this.state = new NullState();
    setFill(color);
  }

  /**
   * Constructs a Player object with specified position, size, and color.
   * @param x The x-coordinate of the player's position.
   * @param y The y-coordinate of the player's position.
   * @param width The width of the player.
   * @param height The height of the player.
   * @param image The image of the player.
  */

  public Player(double x, double y, double width, double height, Image image) {
    super(x, y, width, height);
    this.state = new NullState();
    setFill(new ImagePattern(image));
  }

  /**
   * Moves the player down based on deltatime.
   * @param deltatime The time since the last update.
   */
  
  public void moveDown(double deltaTime) {
    setY(getY() + SPEED * deltaTime);
  }

  /**
   * Moves the player left based on deltatime.
   * @param deltatime The time since the last update.
   */

  public void moveLeft(double deltaTime) {
    setX(getX() - SPEED * deltaTime);
  }

  /**
   * Moves the player right based on deltatime.
   * @param deltatime The time since the last update.
   */

  public void moveRight(double deltaTime) {
    setX(getX() + SPEED * deltaTime);
  }

  /**
   * Moves the player up based on deltatime.
   * @param deltatime The time since the last update.
   */

  public void moveUp(double deltaTime) {
    setY(getY() - SPEED * deltaTime);
  }

  /**
   * Sets state of the player.
   * @param newState The new state of the player.
   */

  public void setState(PlayerState newState) {
    this.state.exit(this);
    this.state = newState;
    this.state.enter(this);
  }

  /**
   * Updates the player based on deltatime.
   * @param deltatime The time since the last update.
   */
  
  public void update(double deltaTime) {
    this.state.update(this, deltaTime);
  }
}
