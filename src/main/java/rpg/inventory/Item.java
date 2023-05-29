package rpg.inventory;

import javafx.scene.image.Image;
import rpg.blocks.Block;

/** Represents an item block. */
public class Item extends Block {
  public String type;
  public int amount = 0;
  private int maxAmount = 64;

  /**
   * Constructs an Item object with the specified coordinates, type, width, and height.
   *
   * @param x The x-coordinate of the item.
   * @param y The y-coordinate of the item.
   * @param type The type of the item.
   * @param width The width of the item.
   * @param height The height of the item.
   */
  public Item(double x, double y, String type, double width, double height) {
    super(x, y, width, height, new Image("sprites/items/" + type + ".png"));
    this.type = type;
    this.amount = 0;
  }

  /**
   * Adds the specified amount to the item.
   *
   * @param x The amount to add.
   */
  public void add(int x) {
    if (inTheLimit(x, true)) {
      this.amount += x;
    }
  }

  /** Adds one unit to the item. */
  public void add() {
    if (inTheLimit(true)) {
      this.amount++;
    }
  }

  /**
   * Removes the specified amount from the item.
   *
   * @param x The amount to remove.
   */
  public void remove(int x) {
    if (inTheLimit(x, false)) {
      this.amount -= x;
    }
    if (this.amount == 0) {
      set("empty");
    }
  }

  /** Removes one unit from the item. */
  public void remove() {
    if (inTheLimit(false)) {
      this.amount--;
    }
    if (this.amount == 0) {
      set("empty");
    }
  }

  /**
   * Checks if the item is within the limit, between 0 and the max amount (64).
   *
   * @return True if the item is within the limit and false otherwise.
   */
  public boolean inTheLimit(boolean isAdd) {
    if (isAdd) {
      if (this.amount < maxAmount) {
        return true;
      }
    } else {
      if (this.amount > 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the item is within the limit, (current amount + x is less than or equal to maxAmount
   * and current amount is greater than or equal to x).
   *
   * @return True if the item is within the limit and false otherwise.
   */
  public boolean inTheLimit(int x, boolean isAdd) {
    if (isAdd) {
      if (this.amount + x <= maxAmount) {
        return true;
      }
    } else {
      if (this.amount >= x) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sets the type and the amount to 1 of the item and changes it's image.
   *
   * @param type The type to set the item to.
   */
  public void set(String type) {
    this.type = type;
    this.amount = 1;
    changeImage(new Image("sprites/items/" + this.type + ".png"));
  }

  /**
   * Sets the type and the amount of the item and changes it's image.
   *
   * @param type The type to set the item to.
   * @param amount The amount to set the item to.
   */
  public void set(String type, int amount) {
    this.type = type;
    this.amount = amount;
    changeImage(new Image("sprites/items/" + this.type + ".png"));
  }
}
