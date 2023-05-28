package rpg.inventory;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import rpg.blocks.Block;
import rpg.inventory.states.InventoryState;
import rpg.inventory.states.NullState;

public class Inventory {

  public Block inventory;
  private InventoryState state;
  public List<List<Slot>> slots;
  public List<List<Item>> items;
  private Color color = Color.BLUE;
  private double itemSpacing = 11;
  private double slotSpacing = 3;
  private int rows = 4;
  private int columns = 8;

  private double x = 1013;
  private double y = 0;
  private double width = 487;
  private double height = 260;
  private int sideBorder = 20;
  private int topBorder = 20;

  private int selectedX;
  private int selectedY;

  Image image = new Image("sprites/inventory.png");

  public Inventory() {
    double itemWidth = 45;
    double itemHeight = 45;

    double innerWidth = width - (sideBorder * 2);
    double innerHeight = height - (topBorder * 2);
    double innerX = x + sideBorder;
    double innerY = y + topBorder;

    double innerXItem = innerX + slotSpacing;
    double innerYItem = innerY + slotSpacing;

    this.inventory = new Block(x, y, width, height, image);
    this.state = new NullState();
    this.items = new ArrayList<>();
    this.slots = new ArrayList<>();
    this.selectedX = 0;
    this.selectedY = 0;

    for (int i = 0; i < rows; i++) {
      List<Item> rowItem = new ArrayList<>();
      List<Slot> rowSlot = new ArrayList<>();

      for (int j = 0; j < columns; j++) {
        double itemX = innerXItem + j * (itemWidth + itemSpacing);
        double itemY = innerYItem + i * (itemHeight + itemSpacing);

        double slotX = innerX + j * (53 + slotSpacing);
        double slotY = innerY + i * (53 + slotSpacing);
        Item itemBlock = new Item(itemX, itemY, "empty", itemWidth, itemHeight);
        Slot slotBlock = new Slot(slotX, slotY);
        rowItem.add(itemBlock);
        rowSlot.add(slotBlock);
      }

      items.add(rowItem);
      slots.add(rowSlot);
      select();
    }
  }

  public void update(double deltaTime) {
    this.inventory.update(deltaTime);
    this.state.update(this);

    for (int i = 0; i < this.items.size(); i++) {
      for (int j = 0; j < this.items.get(i).size(); j++) {
        this.items.get(i).get(j).update(deltaTime);
        this.slots.get(i).get(j).update(deltaTime);
      }
    }
  }

  public String getItem() {
    return this.items.get(selectedY).get(selectedX).type;
  }

  public String getItem(int x, int y) {
    return this.items.get(y).get(x).type;
  }

  public void add(String type) {
    if (!type.equals("empty")) {
      for (int i = 0; i < this.items.size(); i++) {
        for (int j = 0; j < this.items.get(i).size(); j++) {
          if (this.items.get(i).get(j).type.equals(type)) {
            if (this.items.get(i).get(j).inTheLimit()) {
              this.items.get(i).get(j).add();
              return;
            } else {
              break;
            }
          }
        }
      }

      for (int i = 0; i < this.items.size(); i++) {
        for (int j = 0; j < this.items.get(i).size(); j++) {
          if (this.items.get(i).get(j).type.equals("empty")) {
            this.items.get(i).get(j).set(type);
            return;
          }
        }
      }
    }
  }

  public void remove(String type) {
    if (!type.equals("empty")) {
      for (int i = 0; i < this.items.size(); i++) {
        for (int j = 0; j < this.items.get(i).size(); j++) {
          if (this.items.get(i).get(j).type.equals(type)) {
            this.items.get(i).get(j).remove();
            return;
          }
        }
      }
    }
  }

  public void set(int x, int y, int amount, String type) {
    this.items.get(y).get(x).set(type, amount);
  }

  public void select() {
    this.slots.get(selectedY).get(selectedX).select();
  }

  public void unselect() {
    this.slots.get(selectedY).get(selectedX).unselect();
  }

  public void selectUp() {
    if (selectedY > 0) {
      unselect();
      this.selectedY--;
      select();
    }
  }

  public void selectDown() {
    if (selectedY < 3) {
      unselect();
      this.selectedY++;
      select();
    }
  }

  public void selectLeft() {
    if (selectedX > 0) {
      unselect();
      this.selectedX--;
      select();
    }
  }

  public void selectRight() {
    if (selectedX < 7) {
      unselect();
      this.selectedX++;
      select();
    }
  }

  public void setState(InventoryState newState) {
    this.state.exit(this);
    this.state = newState;
    this.state.enter(this);
  }
}
