package rpg.field;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import rpg.blocks.Block;
import rpg.blocks.BlockNullState;
import rpg.plants.Plant;

public class Field {

  public Block field;
  public List<List<Plant>> plants;
  private int rows;
  private double plantSpacing;
  private int columns;

  /*
  public Field(
      double x,
      double y,
      double width,
      double height,
      Color fieldColor,
      Color plantColor,
      int rows,
      int columns,
      double PLANT_SPACING) {
    this.field = new Block(x, y, width, height, fieldColor);
    this.plants = new ArrayList<>();

    double plantWidth = (width - (5 * (columns - 1))) / columns;
    double plantHeight = (height - (PLANT_SPACING * (rows - 1))) / rows;

    for (int i = 0; i < rows; i++) {
      List<Block> row = new ArrayList<>();

      for (int j = 0; j < columns; j++) {
        double plantX = x + j * (plantWidth + 5);
        double plantY = y + i * (plantHeight + PLANT_SPACING);
        Block plantBlock = new Block(plantX, plantY, plantWidth, plantHeight, plantColor);
        row.add(plantBlock);
      }

      plants.add(row);
    }
  }
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

  public void remove(int x, int y) {
    this.plants.get(x).get(y).setState(new BlockNullState());
  }

  public void set(int x, int y, String type) {
    this.plants.get(x).get(y).set(type, "1");
  }

  public void grow(int x, int y) {
    this.plants.get(x).get(y).grow();
  }

  public void reset(int x, int y) {
    this.plants.get(x).get(y).reset();
  }

  public void growAllField() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        grow(i, j);
      }
    }
  }

  public void resetAllField() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        reset(i, j);
      }
    }
  }

  public void update(double deltaTime) {
    this.field.update(deltaTime);

    for (int i = 0; i < this.plants.size(); i++) {
      for (int j = 0; j < this.plants.get(0).size(); j++) {
        this.plants.get(i).get(j).update(deltaTime);
      }
    }
  }
}
