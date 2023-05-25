package rpg.plants;

import javafx.scene.image.Image;
import rpg.blocks.Block;

public class Plant extends Block {
  private String type;
  private String stage = "0";
  private Image image;
  private double width;
  private double height;
  private boolean grown;

  public Plant(double x, double y, double width, double height, String type, Image firstImage) {
    super(x, y, width, height, firstImage);
    this.type = type;
    this.width = width;
    this.height = height;
    this.grown = false;
    grow();
  }

  public void grow() {
    if (stage != "final") {
      int stageInt = Integer.parseInt(this.stage);
      if (stageInt != 3 && !grown) {
        stageInt++;
        this.stage = String.valueOf(stageInt);
      } else {
        this.stage = "final";
        this.grown = true;
      }
    }
    set(this.type, this.stage);
  }

  public void reset() {
    this.stage = "1";
    set(this.type, this.stage);
  }

  public void set(String type, String stage) {
    this.type = type;
    this.image =
        new Image(
            ("sprites/" + type + "/" + type + "_stage_" + stage + ".png"),
            this.width,
            this.height,
            false,
            false);
    changeImage(image);
  }
}
