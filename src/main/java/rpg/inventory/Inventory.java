package rpg.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import rpg.blocks.Block;
import rpg.inventory.states.InventoryState;
import rpg.inventory.states.NullState;
import rpg.text_box.TextBox;

/** Represents the inventory. */
public class Inventory {

  public Block inventory;
  public Block inventoryTextBox;
  public TextBox textBoxInventory;
  private InventoryState state;
  public List<List<Slot>> slots;
  public List<List<Item>> items;
  private double itemSpacing = 11;
  private double slotSpacing = 3;
  private int rows = 4;
  private int columns = 8;

  private double x = 1013;
  private double y = 0;
  private double width = 487;
  private double height = 260;
  private double textBoxHeight = 88;
  private int sideBorder = 20;
  private int topBorder = 20;
  public TextBox textAdd;
  public TextBox textRemove;

  private int selectedX;
  private int selectedY;

  private Map<String, Integer> dictAdd;
  private Map<String, Integer> dictRemove;

  /** Constructs the inventory object. */
  public Inventory() {
    double itemWidth = 45;
    double itemHeight = 45;

    double innerWidth = width - (sideBorder * 2);
    double innerHeight = height - (topBorder * 2);
    double innerX = x + sideBorder;
    double innerY = y + topBorder;

    double innerXItem = innerX + slotSpacing;
    double innerYItem = innerY + slotSpacing;

    this.dictAdd = new HashMap<>();
    this.dictRemove = new HashMap<>();

    this.inventory = new Block(x, y, width, height, new Image("sprites/inventory.png"));
    this.inventoryTextBox =
        new Block(x, y + height, width, textBoxHeight, new Image("sprites/inventoryTextBox.png"));
    this.textBoxInventory = new TextBox("", "", x + 20, y + height + 30);
    this.textBoxInventory.setStyle("-fx-background-color: transparent;-fx-font-size: 20px;");
    this.textAdd = new TextBox("", "", 0, 0, 0);
    this.textAdd.setStyle("-fx-background-color: rgba(186, 186, 186, 0.9);-fx-font-size: 20px;");
    this.textRemove = new TextBox("", "", 0, 0, 25);
    this.textRemove.setStyle("-fx-background-color: rgba(186, 186, 186, 0.9);-fx-font-size: 20px;");

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

  /**
   * Updates the inventory.
   *
   * @param deltaTime The time since the last update.
   */
  public void update(double deltaTime) {
    this.inventory.update(deltaTime);
    this.inventoryTextBox.update(deltaTime);
    this.state.update(this);
    this.textAdd.update(deltaTime);

    if (!this.textAdd.onScreen) {
      this.dictAdd.clear();
    }

    if (!this.textRemove.onScreen) {
      this.dictRemove.clear();
    }

    for (int i = 0; i < this.items.size(); i++) {
      for (int j = 0; j < this.items.get(i).size(); j++) {
        this.items.get(i).get(j).update(deltaTime);
        this.slots.get(i).get(j).update(deltaTime);
      }
    }
  }

  /**
   * Gets the type of the selected item.
   *
   * @return The type of the selected item.
   */
  public String getItem() {
    return this.items.get(selectedY).get(selectedX).type;
  }

  /**
   * Gets the type of the selected item at the specified location.
   *
   * @param x The x-coordinate of the item.
   * @param y The y-coordinate of the item.
   * @return The type of the selected item.
   */
  public String getItem(int x, int y) {
    return this.items.get(y).get(x).type;
  }

  /**
   * Gets the amount of the selected item.
   *
   * @return The amount of the selected item.
   */
  public int getAmount() {
    return this.items.get(selectedY).get(selectedX).amount;
  }

  /**
   * Gets the amount of the selected item at the specified location.
   *
   * @param x The x-coordinate of the item.
   * @param y The y-coordinate of the item.
   * @return The amount of the selected item.
   */
  public int getAmount(int x, int y) {
    return this.items.get(y).get(x).amount;
  }

  /**
   * Manages the text displayed in the inventory.
   *
   * @param type The type of the action (add/remove).
   * @param text The text to be displayed.
   */
  private void textManager(String type, String text) {
    text = text.replace("_", " ");
    Map<String, Integer> dict = null;
    TextBox textBox = null;

    if (type.equals("add")) {
      dict = this.dictAdd;
      textBox = textAdd;
    } else if (type.equals("remove")) {
      type = "remov";
      dict = this.dictRemove;
      textBox = textRemove;
    }

    if (!textBox.onScreen) {
      textBox.setText("Just " + type + "ed: ", text);
      textBox.setTimeUntilExpiration(10 * 1000);
    } else {
      if (textBox.getText().contains(text)) {
        if (!dict.containsKey(text + " x")) {
          dict.put(text + " x", 2);
          textBox.setText(textBox.getText().replace(text, text + " x" + dict.get(text + " x")));
        } else {
          Integer currentValue = dict.get(text + " x");
          dict.put(text + " x", currentValue + 1);
          textBox.setText(
              textBox
                  .getText()
                  .replace(text + " x" + currentValue, text + " x" + dict.get(text + " x")));
        }
      } else {
        textBox.setText(textBox.getText() + " and " + text);
      }
      textBox.setTimeUntilExpiration(10 * 1000);
    }
  }

  /**
   * Adds an item to the inventory.
   *
   * @param type The type of item to be added to the inventory.
   */
  public void add(String type) {
    if (!type.equals("empty")) {
      for (int i = 0; i < this.items.size(); i++) {
        for (int j = 0; j < this.items.get(i).size(); j++) {
          if (this.items.get(i).get(j).type.equals(type)) {
            if (this.items.get(i).get(j).inTheLimit(true)) {
              this.items.get(i).get(j).add();
              textManager("add", type);
              description();
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
            textManager("add", type);
            return;
          }
        }
      }
    }
  }

  /**
   * Removes an item from the inventory.
   *
   * @param type The type of item to be removed from the inventory.
   */
  public void remove(String type) {
    if (!type.equals("empty")) {
      for (int i = 0; i < this.items.size(); i++) {
        for (int j = 0; j < this.items.get(i).size(); j++) {
          if (this.items.get(i).get(j).type.equals(type)) {
            this.items.get(i).get(j).remove();
            textManager("remove", type);
            description();
            return;
          }
        }
      }
    }
  }

  /**
   * Sets the amount and type of an item at a specified position.
   *
   * @param x The x-coordinate of the item.
   * @param y The y-coordinate of the item.
   * @param amount The amount of the item.
   * @param type The type of the item.
   */
  public void set(int x, int y, int amount, String type) {
    this.items.get(y).get(x).set(type, amount);
    description();
  }

  /** Updates the inventory description. */
  public void description() {
    if (getItem().equals("empty")) {
      this.textBoxInventory.setText("", "");
    } else {
      this.textBoxInventory.setText(getItem().replace("_", " "), ", amount: " + getAmount());
    }
  }

  /** Selects the current item. */
  public void select() {
    this.slots.get(selectedY).get(selectedX).select();
    description();
  }

  /** Unselects the current item. */
  public void unselect() {
    this.slots.get(selectedY).get(selectedX).unselect();
  }

  /** Moves selection up. */
  public void selectUp() {
    if (selectedY > 0) {
      unselect();
      this.selectedY--;
      select();
    }
  }

  /** Moves selection down. */
  public void selectDown() {
    if (selectedY < 3) {
      unselect();
      this.selectedY++;
      select();
    }
  }

  /** Moves selection left. */
  public void selectLeft() {
    if (selectedX > 0) {
      unselect();
      this.selectedX--;
      select();
    }
  }

  /** Moves selection right. */
  public void selectRight() {
    if (selectedX < 7) {
      unselect();
      this.selectedX++;
      select();
    }
  }

  /**
   * Sets a new state for the inventory.
   *
   * @param newState The new state to be set.
   */
  public void setState(InventoryState newState) {
    this.state.exit(this);
    this.state = newState;
    this.state.enter(this);
  }
}
