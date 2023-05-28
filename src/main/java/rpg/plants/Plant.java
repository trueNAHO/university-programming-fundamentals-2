package rpg.plants;

import javafx.scene.image.Image;
import rpg.blocks.Block;

public class Plant extends Block {
  private static final String MAX_STAGE_VALUE = "final";
  private static final int MAX_STAGES = 3;
  private static final int START_STAGE = 1;

  public String type;
  private int stage = START_STAGE;
  private Image image;
  private double width;
  private double height;
  public boolean harvestState;
  private boolean grown;

  public Plant(double x, double y, double width, double height, String type, Image firstImage) {
    super(x, y, width, height, firstImage);
    this.type = type;
    this.width = width;
    this.height = height;
    this.harvestState = false;
    this.grown = false;
    grow();
  }

  public void grow() {
    this.setStage(this.stage + 1);
  }

  public void canHarvest(boolean harvest) {
    this.harvestState = harvest;
  }

  public boolean fullyGrown() {
    return this.grown;
  }

  public void reset() {
    this.setStage(START_STAGE);
    this.grown = false;
  }

  public void setStage(int newStage) {
    if (this.stage > MAX_STAGES && newStage > MAX_STAGES) {
      this.grown = true;
      return;
    }

    this.stage = newStage;
    this.SetImage();
  }

  public void setType(String newType) {
    if (newType == this.type) return;

    this.stage = START_STAGE;
    this.grown = false;
    this.type = newType;
    this.SetImage();
  }

  public void SetImage() {
    String stageValue = this.stage <= MAX_STAGES ? String.valueOf(this.stage) : MAX_STAGE_VALUE;

    this.image =
        new Image(
            ("sprites/" + this.type + "/" + this.type + "_stage_" + stageValue + ".png"),
            this.width,
            this.height,
            false,
            false);

    changeImage(this.image);
  }
}
