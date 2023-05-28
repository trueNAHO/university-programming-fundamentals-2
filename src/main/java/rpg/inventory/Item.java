package rpg.inventory;

import javafx.scene.image.Image;
import rpg.blocks.Block;

public class Item extends Block {
  public String type;
  public int amount = 0;
  private int maxAmount = 64;
  // new Image("sprites/TEST.png", width, height, false, false)

  public Item(double x, double y, String type, double width, double height) {
    super(x, y, width, height, new Image("sprites/items/" + type + ".png"));
    this.type = type;
    this.amount = 0;
  }

  public void add(int x) {
    if (inTheLimit(x)) {
      this.amount += x;
    }
  }

  public void add() {
    if (inTheLimit()) {
      this.amount++;
    }
  }

  public void remove(int x) {
    if (inTheLimit(x)) {
      this.amount -= x;
    }
    if (this.amount == 0) {
      set("empty");
    }
  }

  public void remove() {
    if (inTheLimit()) {
      this.amount--;
    }
    if (this.amount == 0) {
      set("empty");
    }
  }

  public boolean inTheLimit() {
    if (this.amount < maxAmount && this.amount > 0) {
      return true;
    } else {
      return false;
    }
  }

  public boolean inTheLimit(int x) {
    if (this.amount + x <= maxAmount && this.amount >= x) {
      return true;
    } else {
      return false;
    }
  }

  public void set(String type) {
    this.type = type;
    this.amount = 1;
    changeImage(new Image("sprites/items/" + this.type + ".png"));
  }

  public void set(String type, int amount) {
    this.type = type;
    this.amount = amount;
    changeImage(new Image("sprites/items/" + this.type + ".png"));
  }
}
