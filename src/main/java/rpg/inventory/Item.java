package rpg.inventory;

import javafx.scene.image.Image;
import rpg.blocks.Block;

public class Item extends Block {
  private String type;
  private int amount = 0;
  private int maxAmount = 64;
  // new Image("sprites/TEST.png", width, height, false, false)

  public Item(double x, double y, String type, double width, double height) {
    super(x, y, width, height, new Image("sprites/TEST.png", width, height, false, false));
    this.type = type;
    this.amount = 0;
  }

  public boolean add(int x) {
    if (this.amount + x <= 64 && x > 0) {
      this.amount += x;
      return true;
    } else {
      return false;
    }
  }

  public boolean add() {
    if (this.amount < 64) {
      this.amount++;
      return true;
    } else {
      return false;
    }
  }

  public boolean remove(int x) {
    if (this.amount >= x && x > 0) {
      this.amount -= x;
      return true;
    } else {
      return false;
    }
  }

  public boolean remove() {
    if (amount > 0) {
      this.amount--;
      return true;
    } else {
      return false;
    }
  }
}
