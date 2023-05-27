package rpg.field;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import rpg.blocks.Block;
import rpg.plants.Plant;

public class Field {

  public Block field;
  public List<List<Plant>> plants;
  private int rows;
  private double plantSpacing;
  private int columns;

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

  public void remove(Plant block) {
    block.setState(new BlockNullState());
    System.out.println("Fuuck you");
    block.setType("empty");
  }

  public void set(int x, int y, String type) {
    this.plants.get(x).get(y).setStage(1);
    this.plants.get(x).get(y).setType(type);
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
        this.reset(i, j);
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
