package rpg.field;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import rpg.blocks.Block;
import rpg.blocks.BlockNullState;
import rpg.inventory.Inventory;
import rpg.plants.Plant;

/**
 * Represents a field in the game where plants can be grown.
*/

public class Field {

  public Block field;
  public List<List<Plant>> plants;
  private int rows;
  private double plantSpacing;
  private int columns;

  /**
   * Constructs a Field object with the specified parameters.
   * @param x The x-coordinate of the field's position.
   * @param y The y-coordinate of the field's position.
   * @param width The width of the field.
   * @param height The height of the field.
   * @param fieldImage The image representing the field.
   * @param plantImage The image representing the plants.
   * @param rows The number of rows in the field.
   * @param columns The number of columns in the field.
   * @param plantSpacing The spacing between plants.
*/
  
  public Field(
      double x,
      double y,
      double width,
      double height,
      Image fieldImage,
      Image plantImage,
      int rows,
      int columns,
      double plantSpacing) {
    this.field = new Block(x, y, width, height, fieldImage);
    this.plantSpacing = plantSpacing;
    this.plants = new ArrayList<>();
    this.rows = rows;
    this.columns = columns;

    double plantWidth = (width - (this.plantSpacing * (columns - 1))) / columns;
    double plantHeight = (height - (this.plantSpacing * (rows - 1))) / rows;

    for (int i = 0; i < rows; i++) {
      List<Plant> row = new ArrayList<>();

      for (int j = 0; j < columns; j++) {
        double plantX = x + j * (plantWidth + this.plantSpacing);
        double plantY = y + i * (plantHeight + this.plantSpacing);
        Plant plantBlock = new Plant(plantX, plantY, plantWidth, plantHeight, "cpu", plantImage);
        row.add(plantBlock);
      }

      plants.add(row);
    }
  }

  /**
   * Removes a plant from the field.
   * @param block The plant to be removed. 
   */

  public void remove(Plant block) {
    block.setState(new BlockNullState());
    block.setType("empty");
  }

  /**
   * Sets the type of a plant at the specified position in the field.
   * @param x The x-coordinate of the position.
   * @param y The y-coordinate of the position.
   * @param type The type of the plant.
   */
  
  public void set(int x, int y, String type) {
    this.plants.get(x).get(y).setStage(1);
    this.plants.get(x).get(y).setType(type);
  }

  /**
   * Harvests a plant if it is fully grown and adds it into the inventory.
   * @param block The plant to be harvested.
   * @param inventory The inventory to add the harvested plant to.
   */
  
  public void harvest(Plant block, Inventory inventory) {
    if (block.fullyGrown()) {
      inventory.add(block.type);
      remove(block);
    }
  }

  /**
   * Plants a new plant to the field, using the seed from the inventory.
   * @param block The plant that will be planted.
   * @param inventory The inventory containing the seed.
   */
  
  public void plant(Plant block, Inventory inventory) {
    String item = inventory.getItem();
    if (!item.equals("empty") && item.contains("seed")) {
      String plantType = item.substring(0, item.length() - 5);
      inventory.remove(item);
      block.setType(plantType);
    }
  }

  /**
   * Grows the plant at the specific location in the field.
   * @param x The x-coordinate of the position.
   * @param y The y-coordinate of the position.
   */
  
  public void grow(int x, int y) {
    this.plants.get(x).get(y).grow();
  }

  /**
   * Resets the plant at the specific location in the field.
   * @param x The x-coordinate of the position.
   * @param y The y-coordinate of the position.
   */

  public void reset(int x, int y) {
    this.plants.get(x).get(y).reset();
  }

  /**
   * Grows all the plants in the field.
   */
  
  public void growAllField() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        grow(i, j);
      }
    }
  }

  /**
   * Resets all the plants in the field.
   */
  
  public void resetAllField() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        this.reset(i, j);
      }
    }
  }

  /**
   * Updates the field and all plants in the field based on the deltaTime.
   * @param deltaTime The time elapsed since the last update.
   */
  
  public void update(double deltaTime) {
    this.field.update(deltaTime);

    for (int i = 0; i < this.plants.size(); i++) {
      for (int j = 0; j < this.plants.get(0).size(); j++) {
        this.plants.get(i).get(j).update(deltaTime);
      }
    }
  }
}
