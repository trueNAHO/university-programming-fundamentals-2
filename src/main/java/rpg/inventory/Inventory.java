package rpg.inventory;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import rpg.blocks.Block;
import rpg.inventory.states.InventoryState;
import rpg.inventory.states.NullState;
import rpg.text_box.TextBox;

public class Inventory {

  public Block inventory;
  public Block inventoryTextBox;
  public TextBox textBoxInventory;
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
  private double textBoxHeight = 88;
  private int sideBorder = 20;
  private int topBorder = 20;
  public TextBox textAdd;
  public TextBox textRemove;

  private int selectedX;
  private int selectedY;

  public Inventory() {
    double itemWidth = 45;
    double itemHeight = 45;

    double innerWidth = width - (sideBorder * 2);
    double innerHeight = height - (topBorder * 2);
    double innerX = x + sideBorder;
    double innerY = y + topBorder;

    double innerXItem = innerX + slotSpacing;
    double innerYItem = innerY + slotSpacing;

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

  public void update(double deltaTime) {
    this.inventory.update(deltaTime);
    this.inventoryTextBox.update(deltaTime);
    this.state.update(this);
    this.textAdd.update(deltaTime);

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

  public int getAmount() {
    return this.items.get(selectedY).get(selectedX).amount;
  }

  public int getAmount(int x, int y) {
    return this.items.get(y).get(x).amount;
  }

  private void textManager(String type, String text) {
    int occurrence = 0;
    text = text.replace("_", " ");
    if (type.equals("add") && !textAdd.onScreen) {
      this.textAdd.setText("Just added: ", text);
      this.textAdd.setTimeUntilExpiration(10 * 1000);
    } else if (type.equals("add") && textAdd.onScreen) {
      if (textAdd.getText().contains(text)) {
        if (textAdd.getText().contains(text + " x")) {
          if (textAdd.getText().contains(" and")
              && textAdd.getText().indexOf(" and") > textAdd.getText().indexOf(text + " x")) {
            occurrence =
                Integer.parseInt(
                    textAdd
                        .getText()
                        .substring(
                            textAdd.getText().indexOf(text + " x") + (text + " x").length(),
                            textAdd.getText().indexOf(" and")));
          } else {
            occurrence =
                Integer.parseInt(
                    textAdd
                        .getText()
                        .substring(
                            textAdd.getText().indexOf(text + " x") + (text + " x").length()));
          }

          this.textAdd.setText(
              textAdd
                  .getText()
                  .replace(
                      (text + " x" + String.valueOf(occurrence)),
                      (text + " x" + String.valueOf(occurrence + 1))));

        } else {
          this.textAdd.setText(textAdd.getText().replace(text, text + " x2"));
        }
      } else {
        this.textAdd.setText(textAdd.getText() + " and " + text);
      }
      this.textAdd.setTimeUntilExpiration(10 * 1000);
    }

    if (type.equals("remove") && !textRemove.onScreen) {
      this.textRemove.setText("Just removed: ", text);
      this.textRemove.setTimeUntilExpiration(10 * 1000);
    } else if (type.equals("remove") && textRemove.onScreen) {
      if (textRemove.getText().contains(text)) {
        if (textRemove.getText().contains(text + " x")) {
          if (textRemove.getText().contains(" and")
              && textRemove.getText().indexOf(" and") > textRemove.getText().indexOf(text + " x")) {
            occurrence =
                Integer.parseInt(
                    textRemove
                        .getText()
                        .substring(
                            textRemove.getText().indexOf(text + " x") + (text + " x").length(),
                            textRemove.getText().indexOf(" and")));
          } else {
            occurrence =
                Integer.parseInt(
                    textRemove
                        .getText()
                        .substring(
                            textRemove.getText().indexOf(text + " x") + (text + " x").length()));
          }
          this.textRemove.setText(
              textRemove
                  .getText()
                  .replace(
                      (text + " x" + String.valueOf(occurrence)),
                      text + " x" + String.valueOf(occurrence + 1)));

        } else {
          this.textRemove.setText(textRemove.getText().replace(text, text + " x2"));
        }
      } else {
        this.textRemove.setText(textRemove.getText() + " and " + text);
      }
      this.textRemove.setTimeUntilExpiration(10 * 1000);
    }
  }

  public void add(String type) {
    if (!type.equals("empty")) {
      for (int i = 0; i < this.items.size(); i++) {
        for (int j = 0; j < this.items.get(i).size(); j++) {
          if (this.items.get(i).get(j).type.equals(type)) {
            if (this.items.get(i).get(j).inTheLimit()) {
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

  public void set(int x, int y, int amount, String type) {
    this.items.get(y).get(x).set(type, amount);
    description();
  }

  public void description() {
    if (getItem().equals("empty")) {
      this.textBoxInventory.setText("", "");
    } else {
      this.textBoxInventory.setText(getItem().replace("_", " "), ", amount: " + getAmount());
    }
  }

  public void select() {
    this.slots.get(selectedY).get(selectedX).select();
    description();
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
