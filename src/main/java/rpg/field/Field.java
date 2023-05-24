package rpg.field;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import rpg.blocks.Block;

public class Field {

  public Block field;
  public List<List<Block>> plants;

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

  public void update(double deltaTime) {
    this.field.update(deltaTime);

    for (int i = 0; i < this.plants.size(); i++) {
      for (int j = 0; j < this.plants.get(0).size(); j++) {
        this.plants.get(i).get(j).update(deltaTime);
      }
    }
  }
}
